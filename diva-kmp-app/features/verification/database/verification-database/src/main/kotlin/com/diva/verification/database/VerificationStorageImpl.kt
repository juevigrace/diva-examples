package com.diva.verification.database

import com.diva.database.DivaDB
import com.diva.models.server.UserVerification
import io.github.juevigrace.diva.core.DivaResult
import io.github.juevigrace.diva.core.Option
import io.github.juevigrace.diva.core.database.DatabaseAction
import io.github.juevigrace.diva.core.errors.DivaError
import io.github.juevigrace.diva.core.errors.DivaErrorException
import io.github.juevigrace.diva.database.DivaDatabase
import kotlinx.coroutines.flow.Flow
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.util.UUID
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.toJavaInstant
import kotlin.time.toKotlinInstant
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid
import kotlin.uuid.toJavaUuid
import kotlin.uuid.toKotlinUuid

class VerificationStorageImpl(
    private val db: DivaDatabase<DivaDB>
) : VerificationStorage {
    @OptIn(ExperimentalUuidApi::class)
    override suspend fun getById(id: Uuid): DivaResult<Option<UserVerification>, DivaError.DatabaseError> {
        return db.getOne { emailVerificationTokensQueries.findOneByUserId(id.toJavaUuid(), mapper = ::mapToEntity) }
    }

    @OptIn(ExperimentalUuidApi::class)
    override fun getByIdFlow(id: Uuid): Flow<DivaResult<Option<UserVerification>, DivaError.DatabaseError>> {
        return db.getOneAsFlow {
            emailVerificationTokensQueries.findOneByUserId(id.toJavaUuid(), mapper = ::mapToEntity)
        }
    }

    override suspend fun getByToken(token: String): DivaResult<Option<UserVerification>, DivaError.DatabaseError> {
        return db.getOne { emailVerificationTokensQueries.findOneByToken(token, mapper = ::mapToEntity) }
    }

    @OptIn(ExperimentalUuidApi::class, ExperimentalTime::class)
    override suspend fun insert(item: UserVerification): DivaResult<Unit, DivaError.DatabaseError> {
        return db.use {
            val rows: Long = transactionWithResult {
                val expiresAt: OffsetDateTime = OffsetDateTime.ofInstant(
                    item.expiresAt.toJavaInstant(),
                    ZoneOffset.systemDefault()
                )

                emailVerificationTokensQueries.insert(
                    user_id = item.userId.toJavaUuid(),
                    token = item.token,
                    expires_at = expiresAt,
                )
            }
            if (rows.toInt() == 0) {
                throw DivaErrorException(
                    DivaError.DatabaseError(
                        DatabaseAction.INSERT,
                        "diva_email_verification_tokens",
                        "Failed to insert"
                    )
                )
            }
            return@use DivaResult.success(Unit)
        }
    }

    // TODO: logic
    @OptIn(ExperimentalUuidApi::class, ExperimentalTime::class)
    override suspend fun update(item: UserVerification): DivaResult<Unit, DivaError.DatabaseError> {
        return db.use {
            val rows: Long = transactionWithResult {
                emailVerificationTokensQueries.update(item.userId.toJavaUuid())
            }
            if (rows.toInt() == 0) {
                throw DivaErrorException(
                    DivaError.DatabaseError(
                        DatabaseAction.UPDATE,
                        "diva_email_verification_tokens",
                        "Failed to insert"
                    )
                )
            }
            return@use DivaResult.success(Unit)
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun delete(id: Uuid): DivaResult<Unit, DivaError.DatabaseError> {
        return db.use {
            val rows: Long = transactionWithResult {
                emailVerificationTokensQueries.delete(id.toJavaUuid())
            }
            if (rows.toInt() == 0) {
                throw DivaErrorException(
                    DivaError.DatabaseError(
                        DatabaseAction.DELETE,
                        "diva_email_verification_tokens",
                        "Failed to delete"
                    )
                )
            }
            return@use DivaResult.success(Unit)
        }
    }

    override suspend fun deleteByToken(token: String): DivaResult<Unit, DivaError.DatabaseError> {
        return db.use {
            val rows: Long = transactionWithResult {
                emailVerificationTokensQueries.deleteByToken(token)
            }
            if (rows.toInt() == 0) {
                throw DivaErrorException(
                    DivaError.DatabaseError(
                        DatabaseAction.DELETE,
                        "diva_email_verification_tokens",
                        "Failed to delete"
                    )
                )
            }
            return@use DivaResult.success(Unit)
        }
    }

    @OptIn(ExperimentalUuidApi::class, ExperimentalTime::class)
    private fun mapToEntity(
        userId: UUID,
        token: String,
        expiresAt: OffsetDateTime,
        createdAt: OffsetDateTime,
        usedAt: OffsetDateTime?
    ): UserVerification {
        return UserVerification(
            userId = userId.toKotlinUuid(),
            token = token,
            expiresAt = expiresAt.toInstant().toKotlinInstant(),
            createdAt = createdAt.toInstant().toKotlinInstant(),
            usedAt = Option.of(usedAt?.toInstant()?.toKotlinInstant())
        )
    }
}
