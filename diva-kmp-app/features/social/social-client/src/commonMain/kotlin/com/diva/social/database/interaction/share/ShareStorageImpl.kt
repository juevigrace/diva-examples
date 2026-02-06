package com.diva.social.database.interaction.share

import com.diva.database.DivaDB
import com.diva.database.social.share.ShareStorage
import com.diva.models.social.interaction.share.Share
import io.github.juevigrace.diva.core.DivaResult
import io.github.juevigrace.diva.core.Option
import io.github.juevigrace.diva.core.database.DatabaseAction
import io.github.juevigrace.diva.core.errors.DivaError
import io.github.juevigrace.diva.core.errors.ErrorCause
import io.github.juevigrace.diva.database.DivaDatabase
import kotlinx.coroutines.flow.Flow
import kotlin.time.ExperimentalTime
import kotlin.time.Instant
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

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
        return db.getOne { shareQueries.findOneById(id.toString(), mapper = ::mapToEntity) }
    }

    @OptIn(ExperimentalUuidApi::class)
    override fun getByIdFlow(id: Uuid): Flow<DivaResult<Option<Share>, DivaError>> {
        return db.getOneAsFlow { shareQueries.findOneById(id.toString(), mapper = ::mapToEntity) }
    }

    @OptIn(ExperimentalTime::class, ExperimentalUuidApi::class)
    override suspend fun insert(item: Share): DivaResult<Unit, DivaError> {
        return db.use {
            val rows: Long = transactionWithResult {
                shareQueries.insert(
                    id = item.id.toString(),
                    user_id = item.userId.toString(),
                    content_id = item.contentId.toString(),
                    content_type = item.contentType,
                    shared_at = item.sharedAt.toEpochMilliseconds(),
                    created_at = item.createdAt.toEpochMilliseconds(),
                    updated_at = item.updatedAt.toEpochMilliseconds(),
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
                    user_id = item.userId.toString(),
                    content_id = item.contentId.toString(),
                    content_type = item.contentType,
                    shared_at = item.sharedAt.toEpochMilliseconds(),
                    created_at = item.createdAt.toEpochMilliseconds(),
                    updated_at = item.updatedAt.toEpochMilliseconds(),
                    id = item.id.toString()
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
                shareQueries.delete(id.toString())
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
        id: String,
        userId: String,
        contentId: String,
        contentType: String,
        sharedAt: Long,
        createdAt: Long,
        updatedAt: Long
    ): Share {
        return Share(
            id = Uuid.parse(id),
            userId = Uuid.parse(userId),
            contentId = Uuid.parse(contentId),
            contentType = contentType,
            sharedAt = Instant.fromEpochMilliseconds(sharedAt),
            createdAt = Instant.fromEpochMilliseconds(createdAt),
            updatedAt = Instant.fromEpochMilliseconds(updatedAt)
        )
    }
}
