package com.diva.verification.data

import com.diva.models.server.UserVerification
import com.diva.verification.database.VerificationStorage
import io.github.juevigrace.diva.core.DivaResult
import io.github.juevigrace.diva.core.database.DatabaseAction
import io.github.juevigrace.diva.core.errors.DivaError
import io.github.juevigrace.diva.core.errors.DivaErrorException
import io.github.juevigrace.diva.core.errors.toDivaError
import io.github.juevigrace.diva.core.fold
import io.github.juevigrace.diva.core.isPresent
import io.github.juevigrace.diva.core.map
import io.github.juevigrace.diva.core.onFailure
import io.github.juevigrace.diva.core.tryResult
import kotlin.time.Clock
import kotlin.time.Duration.Companion.minutes
import kotlin.time.ExperimentalTime
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class VerificationServiceImpl(
    private val storage: VerificationStorage
) : VerificationService {
    @OptIn(ExperimentalUuidApi::class, ExperimentalTime::class)
    override suspend fun createVerification(userId: Uuid): DivaResult<UserVerification, DivaError> {
        return tryResult(
            onError = { e -> e.toDivaError() }
        ) {
            val item = UserVerification(
                userId = userId,
                token = generateToken(),
                expiresAt = Clock.System.now().plus(15.minutes),
                createdAt = Clock.System.now(),
            )
            storage.insert(item).onFailure { err -> throw DivaErrorException(err) }
            storage.getById(userId)
                .onFailure { err -> throw DivaErrorException(err) }
                .map { option ->
                    option.fold(
                        onNone = {
                            throw DivaErrorException(
                                DivaError.DatabaseError(
                                    DatabaseAction.SELECT,
                                    "diva_email_verification_tokens",
                                    "Failed to get verification"
                                )
                            )
                        },
                        onSome = { it }
                    )
                }
        }
    }

    @OptIn(ExperimentalTime::class)
    override suspend fun verify(token: String): DivaResult<Unit, DivaError> {
        return tryResult(
            onError = { e -> e.toDivaError() }
        ) {
            storage.getByToken(token).fold(
                onFailure = { err -> throw DivaErrorException(err) },
                onSuccess = { option ->
                    option.fold(
                        onNone = {
                            throw DivaErrorException(
                                DivaError.DatabaseError(
                                    DatabaseAction.SELECT,
                                    "diva_email_verification_tokens",
                                    "Failed to get verification"
                                )
                            )
                        },
                        onSome = { verification ->
                            when {
                                verification.expiresAt < Clock.System.now() -> {
                                    throw DivaErrorException(
                                        DivaError.ValidationError(
                                            "expiresAt",
                                            "Verification has expired"
                                        )
                                    )
                                }
                                verification.usedAt.isPresent() -> {
                                    throw DivaErrorException(
                                        DivaError.ValidationError(
                                            "usedAt",
                                            "Verification has already been used"
                                        )
                                    )
                                }
                                else -> {
                                    storage.deleteByToken(token)
                                }
                            }
                        }
                    )
                }
            )
        }
    }

    private fun generateToken(): String {
        return ""
    }
}
