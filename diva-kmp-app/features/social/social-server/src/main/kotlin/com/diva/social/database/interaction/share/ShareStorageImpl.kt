package com.diva.social.database.interaction.share

import com.diva.database.DivaDB
import com.diva.database.social.share.ShareStorage
import com.diva.models.social.interaction.share.Share
import com.diva.models.user.User
import io.github.juevigrace.diva.core.DivaResult
import io.github.juevigrace.diva.core.Option
import io.github.juevigrace.diva.core.database.DatabaseAction
import io.github.juevigrace.diva.core.errors.DivaError
import io.github.juevigrace.diva.core.errors.ErrorCause
import io.github.juevigrace.diva.database.DivaDatabase
import kotlinx.coroutines.flow.Flow
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

class ShareStorageImpl(
    private val db: DivaDatabase<DivaDB>
) : ShareStorage {
    override suspend fun count(): DivaResult<Long, DivaError> {
        return db.use {
            val value: Long = shareQueries.count().executeAsOne()
            DivaResult.success(value)
        }
    }

    override suspend fun getAll(
        limit: Int,
        offset: Int
    ): DivaResult<List<Share>, DivaError> {
        return db.getList { shareQueries.findAll(limit.toLong(), offset.toLong(), mapper = ::mapToEntity) }
    }

    override fun getAllFlow(
        limit: Int,
        offset: Int
    ): Flow<DivaResult<List<Share>, DivaError>> {
        return db.getListAsFlow { shareQueries.findAll(limit.toLong(), offset.toLong(), mapper = ::mapToEntity) }
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun getById(id: Uuid): DivaResult<Option<Share>, DivaError> {
        return db.getOne { shareQueries.findOneById(id.toJavaUuid(), mapper = ::mapToEntity) }
    }

    @OptIn(ExperimentalUuidApi::class)
    override fun getByIdFlow(id: Uuid): Flow<DivaResult<Option<Share>, DivaError>> {
        return db.getOneAsFlow { shareQueries.findOneById(id.toJavaUuid(), mapper = ::mapToEntity) }
    }

    @OptIn(ExperimentalTime::class, ExperimentalUuidApi::class)
    override suspend fun insert(item: Share): DivaResult<Unit, DivaError> {
        return db.use {
            val rows: Long = transactionWithResult {
                shareQueries.insert(
                    id = item.id.toJavaUuid(),
                    target_id = item.targetId.toJavaUuid(),
                    target_type = item.targetType,
                    user_id = item.userId.toJavaUuid(),
                    share_type = item.shareType,
                )
            }
            if (rows.toInt() == 0) {
                return@use DivaResult.failure(
                    DivaError(
                        ErrorCause.Database.NoRowsAffected(
                            action = DatabaseAction.INSERT,
                            table = Option.Some("diva_share"),
                            details = Option.Some("Failed to insert")
                        )
                    )
                )
            }
            DivaResult.success(Unit)
        }
    }

    @OptIn(ExperimentalTime::class, ExperimentalUuidApi::class)
    override suspend fun update(item: Share): DivaResult<Unit, DivaError> {
        return db.use {
            val rows: Long = transactionWithResult {
                shareQueries.update(
                    target_id = item.targetId.toJavaUuid(),
                    target_type = item.targetType,
                    user_id = item.userId.toJavaUuid(),
                    share_type = item.shareType,
                    id = item.id.toJavaUuid()
                )
            }
            if (rows.toInt() == 0) {
                return@use DivaResult.failure(
                    DivaError(
                        ErrorCause.Database.NoRowsAffected(
                            action = DatabaseAction.UPDATE,
                            table = Option.Some("diva_share"),
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
                shareQueries.delete(id.toJavaUuid())
            }
            if (rows.toInt() == 0) {
                return@use DivaResult.failure(
                    DivaError(
                        ErrorCause.Database.NoRowsAffected(
                            action = DatabaseAction.DELETE,
                            table = Option.Some("diva_share"),
                            details = Option.Some("Failed to delete")
                        )
                    )
                )
            }
            DivaResult.success(Unit)
        }
    }

    @OptIn(ExperimentalTime::class, ExperimentalUuidApi::class)
    private fun mapToEntity(
        id: UUID,
        targetId: UUID,
        targetType: String,
        userId: UUID,
        shareType: String,
        createdAt: OffsetDateTime,
        updatedAt: OffsetDateTime,
        deletedAt: OffsetDateTime?,
        uEmail: String?,
        uUsername: String?,
        uCreatedAt: OffsetDateTime?,
        uUpdatedAt: OffsetDateTime?,
    ): Share {
        require(uEmail != null) { "User email cannot be null" }
        require(uUsername != null) { "User username cannot be null" }
        require(uCreatedAt != null) { "User created at cannot be null" }
        require(uUpdatedAt != null) { "User updated at cannot be null" }

        return Share(
            id = id.toKotlinUuid(),
            targetId = targetId.toKotlinUuid(),
            targetType = targetType,
            userId = User(
                id = userId.toKotlinUuid(),
                email = uEmail,
                username = uUsername,
                createdAt = uCreatedAt.toInstant().toKotlinInstant(),
                updatedAt = uUpdatedAt.toInstant().toKotlinInstant()
            ),
            shareType = shareType,
            createdAt = createdAt.toInstant().toKotlinInstant(),
            updatedAt = updatedAt.toInstant().toKotlinInstant(),
            deletedAt = Option.of(deletedAt?.toInstant()?.toKotlinInstant()),
        )
    }
}
