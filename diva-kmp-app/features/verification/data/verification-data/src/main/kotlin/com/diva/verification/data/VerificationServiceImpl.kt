package com.diva.verification.data

import com.diva.models.server.UserVerification
import com.diva.verification.database.VerificationStorage
import io.github.juevigrace.diva.core.DivaResult
import io.github.juevigrace.diva.core.errors.DivaError
import io.github.juevigrace.diva.core.errors.toDivaError
import io.github.juevigrace.diva.core.flatMap
import io.github.juevigrace.diva.core.fold
import io.github.juevigrace.diva.core.isPresent
import io.github.juevigrace.diva.core.tryResult
import kotlin.random.Random
import kotlin.time.Clock
import kotlin.time.Duration.Companion.minutes
import kotlin.time.ExperimentalTime
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class VerificationServiceImpl(
    private val storage: VerificationStorage
) : VerificationService {
    @OptIn(ExperimentalUuidApi::class, ExperimentalTime::class)
    override suspend fun createVerificationCode(userId: Uuid): DivaResult<UserVerification, DivaError> {
        return tryResult(
            onError = { e -> e.toDivaError() }
        ) {
            val item = UserVerification(
                userId = userId,
                token = generateToken(),
                expiresAt = Clock.System.now().plus(15.minutes),
                createdAt = Clock.System.now(),
            )

            storage.insert(item)
                .flatMap { DivaResult.success(item) }
        }
    }

    @OptIn(ExperimentalTime::class)
    override suspend fun verify(token: String): DivaResult<Unit, DivaError> {
        return storage
            .getByToken(token)
            .flatMap { option ->
                option.fold(
                    onNone = {
                        DivaResult.failure(DivaError.ValidationError("token", "Token doesn't exists"))
                    },
                    onSome = { verification ->
                        when {
                            verification.expiresAt < Clock.System.now() -> {
                                DivaResult.failure(DivaError.ValidationError("token", "Token has expired"))
                            }
                            verification.usedAt.isPresent() -> {
                                DivaResult.failure(
                                    DivaError.ValidationError("token", "Token has already been used")
                                )
                            }
                            else -> {
                                storage.update(verification)
                            }
                        }
                    }
                )
            }
    }

    override suspend fun deleteToken(token: String): DivaResult<Unit, DivaError> {
        return storage.deleteByToken(token)
    }

    private fun generateToken(): String {
        return Random.nextInt(LOWER_BOUND, UPPER_BOUND).toString()
    }

    companion object {
        private const val LOWER_BOUND = 100000
        private const val UPPER_BOUND = 999999
    }
}
