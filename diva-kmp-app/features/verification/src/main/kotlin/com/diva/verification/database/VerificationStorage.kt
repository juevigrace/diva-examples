package com.diva.verification.database

import com.diva.database.DivaDB
import com.diva.models.server.UserVerification
import io.github.juevigrace.diva.core.DivaResult
import io.github.juevigrace.diva.core.Option
import io.github.juevigrace.diva.core.database.DatabaseAction
import io.github.juevigrace.diva.core.errors.DivaError
import io.github.juevigrace.diva.core.errors.ErrorCause
import io.github.juevigrace.diva.database.DivaDatabase
import io.github.juevigrace.diva.database.Storage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.util.UUID
import kotlin.time.ExperimentalTime
import kotlin.time.toJavaInstant
import kotlin.time.toKotlinInstant
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid
import kotlin.uuid.toJavaUuid
import kotlin.uuid.toKotlinUuid

interface VerificationStorage : Storage<UserVerification> {
    suspend fun getByToken(token: String): DivaResult<Option<UserVerification>, DivaError>
    suspend fun deleteByToken(token: String): DivaResult<Unit, DivaError>

    @OptIn(ExperimentalUuidApi::class)
    suspend fun verifyUser(userId: Uuid): DivaResult<Unit, DivaError>

    override suspend fun count(): DivaResult<Long, DivaError> {
        return DivaResult.Failure(
            DivaError(cause = ErrorCause.Error.NotImplemented(Option.Some("database: action not supported")))
        )
    }

    override suspend fun getAll(
        limit: Int,
        offset: Int
    ): DivaResult<List<UserVerification>, DivaError> {
        return DivaResult.Failure(
            DivaError(cause = ErrorCause.Error.NotImplemented(Option.Some("database: action not supported")))
        )
    }

    override fun getAllFlow(
        limit: Int,
        offset: Int
    ): Flow<DivaResult<List<UserVerification>, DivaError>> {
        return flowOf(
            DivaResult.Failure(
                DivaError(cause = ErrorCause.Error.NotImplemented(Option.Some("database: action not supported")))
            )
        )
    }
}

class VerificationStorageImpl(
    private val db: DivaDatabase<DivaDB>
) : VerificationStorage {
    @OptIn(ExperimentalUuidApi::class)
    override suspend fun getById(id: Uuid): DivaResult<Option<UserVerification>, DivaError> {
        return db.getOne { emailVerificationTokensQueries.findOneByUserId(id.toJavaUuid(), mapper = ::mapToEntity) }
    }

    @OptIn(ExperimentalUuidApi::class)
    override fun getByIdFlow(id: Uuid): Flow<DivaResult<Option<UserVerification>, DivaError>> {
        return db.getOneAsFlow {
            emailVerificationTokensQueries.findOneByUserId(id.toJavaUuid(), mapper = ::mapToEntity)
        }
    }

    override suspend fun getByToken(token: String): DivaResult<Option<UserVerification>, DivaError> {
        return db.getOne { emailVerificationTokensQueries.findOneByToken(token, mapper = ::mapToEntity) }
    }

    @OptIn(ExperimentalUuidApi::class, ExperimentalTime::class)
    override suspend fun insert(item: UserVerification): DivaResult<Unit, DivaError> {
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
                ).value
            }
            if (rows.toInt() == 0) {
                return@use DivaResult.failure(
                    DivaError(
                        cause = ErrorCause.Database.NoRowsAffected(
                            action = DatabaseAction.INSERT,
                            table = Option.Some("diva_email_verification_tokens"),
                            details = Option.Some("Failed to insert")
                        )
                    )
                )
            }
            DivaResult.success(Unit)
        }
    }

    @OptIn(ExperimentalUuidApi::class, ExperimentalTime::class)
    override suspend fun update(item: UserVerification): DivaResult<Unit, DivaError> {
        return db.use {
            val rows: Long = transactionWithResult {
                emailVerificationTokensQueries.update(item.userId.toJavaUuid()).value
            }
            if (rows.toInt() == 0) {
                return@use DivaResult.failure(
                    DivaError(
                        cause = ErrorCause.Database.NoRowsAffected(
                            action = DatabaseAction.UPDATE,
                            table = Option.Some("diva_email_verification_tokens"),
                            details = Option.Some("Failed to update")
                        )
                    )
                )
            }
            DivaResult.success(Unit)
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun verifyUser(userId: Uuid): DivaResult<Unit, DivaError> {
        return db.use {
            val rows: Long = transactionWithResult {
                userQueries.updateVerified(true, userId.toJavaUuid()).value
            }
            if (rows.toInt() == 0) {
                return@use DivaResult.failure(
                    DivaError(
                        cause = ErrorCause.Database.NoRowsAffected(
                            action = DatabaseAction.UPDATE,
                            table = Option.Some("diva_user"),
                            details = Option.Some("Failed to update")
                        )
                    )
                )
            }
            DivaResult.success(Unit)
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun delete(id: Uuid): DivaResult<Unit, DivaError> {
        return db.use {
            val rows: Long = transactionWithResult {
                emailVerificationTokensQueries.delete(id.toJavaUuid()).value
            }
            if (rows.toInt() == 0) {
                return@use DivaResult.failure(
                    DivaError(
                        cause = ErrorCause.Database.NoRowsAffected(
                            action = DatabaseAction.DELETE,
                            table = Option.Some("diva_email_verification_tokens"),
                            details = Option.Some("Failed to delete")
                        )
                    )
                )
            }
            DivaResult.success(Unit)
        }
    }

    override suspend fun deleteByToken(token: String): DivaResult<Unit, DivaError> {
        return db.use {
            val rows: Long = transactionWithResult {
                emailVerificationTokensQueries.deleteByToken(token).value
            }
            if (rows.toInt() == 0) {
                return@use DivaResult.failure(
                    DivaError(
                        cause = ErrorCause.Database.NoRowsAffected(
                            action = DatabaseAction.DELETE,
                            table = Option.Some("diva_email_verification_tokens"),
                            details = Option.Some("Failed to delete")
                        )
                    )
                )
            }
            DivaResult.success(Unit)
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
