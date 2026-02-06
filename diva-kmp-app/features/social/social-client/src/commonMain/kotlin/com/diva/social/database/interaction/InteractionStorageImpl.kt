package com.diva.social.database.interaction

import com.diva.database.DivaDB
import com.diva.database.social.interaction.InteractionStorage
import com.diva.models.social.interaction.Interaction
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

class InteractionStorageImpl(
    private val db: DivaDatabase<DivaDB>
) : InteractionStorage {
    override suspend fun count(): DivaResult<Long, DivaError> {
        return db.use {
            val value: Long = interactionQueries.count().executeAsOne()
            DivaResult.success(value)
        }
    }

    override suspend fun getAll(
        limit: Int,
        offset: Int
    ): DivaResult<List<Interaction>, DivaError> {
        return db.getList { interactionQueries.findAll(limit.toLong(), offset.toLong(), mapper = ::mapToEntity) }
    }

    override fun getAllFlow(
        limit: Int,
        offset: Int
    ): Flow<DivaResult<List<Interaction>, DivaError>> {
        return db.getListAsFlow { interactionQueries.findAll(limit.toLong(), offset.toLong(), mapper = ::mapToEntity) }
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun getById(id: Uuid): DivaResult<Option<Interaction>, DivaError> {
        return db.getOne { interactionQueries.findOneById(id.toString(), mapper = ::mapToEntity) }
    }

    @OptIn(ExperimentalUuidApi::class)
    override fun getByIdFlow(id: Uuid): Flow<DivaResult<Option<Interaction>, DivaError>> {
        return db.getOneAsFlow { interactionQueries.findOneById(id.toString(), mapper = ::mapToEntity) }
    }

    @OptIn(ExperimentalTime::class, ExperimentalUuidApi::class)
    override suspend fun insert(item: Interaction): DivaResult<Unit, DivaError> {
        return db.use {
            val rows: Long = transactionWithResult {
                interactionQueries.insert(
                    id = item.id.toString(),
                    user_id = item.userId.toString(),
                    content_id = item.contentId.toString(),
                    content_type = item.contentType,
                    interaction_type = item.interactionType,
                    interacted_at = item.interactedAt.toEpochMilliseconds(),
                    created_at = item.createdAt.toEpochMilliseconds(),
                    updated_at = item.updatedAt.toEpochMilliseconds(),
                )
            }
            if (rows.toInt() == 0) {
                return@use DivaResult.failure(
                    DivaError(
                        ErrorCause.Database.NoRowsAffected(
                            action = DatabaseAction.INSERT,
                            table = Option.Some("diva_interaction"),
                            details = Option.Some("Failed to insert")
                        )
                    )
                )
            }
            DivaResult.success(Unit)
        }
    }

    @OptIn(ExperimentalTime::class, ExperimentalUuidApi::class)
    override suspend fun update(item: Interaction): DivaResult<Unit, DivaError> {
        return db.use {
            val rows: Long = transactionWithResult {
                interactionQueries.update(
                    user_id = item.userId.toString(),
                    content_id = item.contentId.toString(),
                    content_type = item.contentType,
                    interaction_type = item.interactionType,
                    interacted_at = item.interactedAt.toEpochMilliseconds(),
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
                            table = Option.Some("diva_interaction"),
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
                interactionQueries.delete(id.toString())
            }
            if (rows.toInt() == 0) {
                return@use DivaResult.failure(
                    DivaError(
                        ErrorCause.Database.NoRowsAffected(
                            action = DatabaseAction.DELETE,
                            table = Option.Some("diva_interaction"),
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
        interactionType: String,
        interactedAt: Long,
        createdAt: Long,
        updatedAt: Long
    ): Interaction {
        return Interaction(
            id = Uuid.parse(id),
            userId = Uuid.parse(userId),
            contentId = Uuid.parse(contentId),
            contentType = contentType,
            interactionType = interactionType,
            interactedAt = Instant.fromEpochMilliseconds(interactedAt),
            createdAt = Instant.fromEpochMilliseconds(createdAt),
            updatedAt = Instant.fromEpochMilliseconds(updatedAt)
        )
    }
}
