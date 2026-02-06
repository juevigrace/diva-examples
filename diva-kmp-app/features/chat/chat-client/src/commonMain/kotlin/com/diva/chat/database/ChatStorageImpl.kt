package com.diva.chat.database

import com.diva.database.DivaDB
import com.diva.database.chat.ChatStorage
import com.diva.models.chat.Chat
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

class ChatStorageImpl(
    private val db: DivaDatabase<DivaDB>
) : ChatStorage {
    override suspend fun count(): DivaResult<Long, DivaError> {
        return db.use {
            val value: Long = chatQueries.count().executeAsOne()
            DivaResult.success(value)
        }
    }

    override suspend fun getAll(
        limit: Int,
        offset: Int
    ): DivaResult<List<Chat>, DivaError> {
        return db.getList { chatQueries.findAll(limit.toLong(), offset.toLong(), mapper = ::mapToEntity) }
    }

    override fun getAllFlow(
        limit: Int,
        offset: Int
    ): Flow<DivaResult<List<Chat>, DivaError>> {
        return db.getListAsFlow { chatQueries.findAll(limit.toLong(), offset.toLong(), mapper = ::mapToEntity) }
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun getById(id: Uuid): DivaResult<Option<Chat>, DivaError> {
        return db.getOne { chatQueries.findOneById(id.toString(), mapper = ::mapToEntity) }
    }

    @OptIn(ExperimentalUuidApi::class)
    override fun getByIdFlow(id: Uuid): Flow<DivaResult<Option<Chat>, DivaError>> {
        return db.getOneAsFlow { chatQueries.findOneById(id.toString(), mapper = ::mapToEntity) }
    }

    @OptIn(ExperimentalTime::class, ExperimentalUuidApi::class)
    override suspend fun insert(item: Chat): DivaResult<Unit, DivaError> {
        return db.use {
            val rows: Long = transactionWithResult {
                chatQueries.insert(
                    id = item.id.toString(),
                    title = item.title,
                    description = item.description,
                    type = item.type,
                    created_by = item.createdBy.toString(),
                    created_at = item.createdAt.toEpochMilliseconds(),
                    updated_at = item.updatedAt.toEpochMilliseconds(),
                )
            }
            if (rows.toInt() == 0) {
                return@use DivaResult.failure(
                    DivaError(
                        ErrorCause.Database.NoRowsAffected(
                            action = DatabaseAction.INSERT,
                            table = Option.Some("diva_chat"),
                            details = Option.Some("Failed to insert")
                        )
                    )
                )
            }
            DivaResult.success(Unit)
        }
    }

    @OptIn(ExperimentalTime::class, ExperimentalUuidApi::class)
    override suspend fun update(item: Chat): DivaResult<Unit, DivaError> {
        return db.use {
            val rows: Long = transactionWithResult {
                chatQueries.update(
                    title = item.title,
                    description = item.description,
                    type = item.type,
                    created_by = item.createdBy.toString(),
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
                            table = Option.Some("diva_chat"),
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
                chatQueries.delete(id.toString())
            }
            if (rows.toInt() == 0) {
                return@use DivaResult.failure(
                    DivaError(
                        ErrorCause.Database.NoRowsAffected(
                            action = DatabaseAction.DELETE,
                            table = Option.Some("diva_chat"),
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
        title: String,
        description: String,
        type: String,
        createdBy: String,
        createdAt: Long,
        updatedAt: Long
    ): Chat {
        return Chat(
            id = Uuid.parse(id),
            title = title,
            description = description,
            type = type,
            createdBy = Uuid.parse(createdBy),
            createdAt = Instant.fromEpochMilliseconds(createdAt),
            updatedAt = Instant.fromEpochMilliseconds(updatedAt)
        )
    }
}
