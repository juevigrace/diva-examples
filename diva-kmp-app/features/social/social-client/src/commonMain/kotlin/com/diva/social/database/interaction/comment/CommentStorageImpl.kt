package com.diva.social.database.interaction.comment

import com.diva.database.DivaDB
import com.diva.database.social.comment.CommentStorage
import com.diva.models.social.interaction.comment.Comment
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

class CommentStorageImpl(
    private val db: DivaDatabase<DivaDB>
) : CommentStorage {
    override suspend fun count(): DivaResult<Long, DivaError> {
        return db.use {
            val value: Long = commentQueries.count().executeAsOne()
            DivaResult.success(value)
        }
    }

    override suspend fun getAll(
        limit: Int,
        offset: Int
    ): DivaResult<List<Comment>, DivaError> {
        return db.getList { commentQueries.findAll(limit.toLong(), offset.toLong(), mapper = ::mapToEntity) }
    }

    override fun getAllFlow(
        limit: Int,
        offset: Int
    ): Flow<DivaResult<List<Comment>, DivaError>> {
        return db.getListAsFlow { commentQueries.findAll(limit.toLong(), offset.toLong(), mapper = ::mapToEntity) }
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun getById(id: Uuid): DivaResult<Option<Comment>, DivaError> {
        return db.getOne { commentQueries.findOneById(id.toString(), mapper = ::mapToEntity) }
    }

    @OptIn(ExperimentalUuidApi::class)
    override fun getByIdFlow(id: Uuid): Flow<DivaResult<Option<Comment>, DivaError>> {
        return db.getOneAsFlow { commentQueries.findOneById(id.toString(), mapper = ::mapToEntity) }
    }

    @OptIn(ExperimentalTime::class, ExperimentalUuidApi::class)
    override suspend fun insert(item: Comment): DivaResult<Unit, DivaError> {
        return db.use {
            val rows: Long = transactionWithResult {
                commentQueries.insert(
                    id = item.id.toString(),
                    content = item.content,
                    author_id = item.authorId.toString(),
                    post_id = item.postId.toString(),
                    parent_id = item.parentId?.toString(),
                    status = item.status,
                    created_at = item.createdAt.toEpochMilliseconds(),
                    updated_at = item.updatedAt.toEpochMilliseconds(),
                )
            }
            if (rows.toInt() == 0) {
                return@use DivaResult.failure(
                    DivaError(
                        ErrorCause.Database.NoRowsAffected(
                            action = DatabaseAction.INSERT,
                            table = Option.Some("diva_comment"),
                            details = Option.Some("Failed to insert")
                        )
                    )
                )
            }
            DivaResult.success(Unit)
        }
    }

    @OptIn(ExperimentalTime::class, ExperimentalUuidApi::class)
    override suspend fun update(item: Comment): DivaResult<Unit, DivaError> {
        return db.use {
            val rows: Long = transactionWithResult {
                commentQueries.update(
                    content = item.content,
                    author_id = item.authorId.toString(),
                    post_id = item.postId.toString(),
                    parent_id = item.parentId?.toString(),
                    status = item.status,
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
                            table = Option.Some("diva_comment"),
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
                commentQueries.delete(id.toString())
            }
            if (rows.toInt() == 0) {
                return@use DivaResult.failure(
                    DivaError(
                        ErrorCause.Database.NoRowsAffected(
                            action = DatabaseAction.DELETE,
                            table = Option.Some("diva_comment"),
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
        content: String,
        authorId: String,
        postId: String,
        parentId: String?,
        status: String,
        createdAt: Long,
        updatedAt: Long
    ): Comment {
        return Comment(
            id = Uuid.parse(id),
            content = content,
            authorId = Uuid.parse(authorId),
            postId = Uuid.parse(postId),
            parentId = parentId?.let { Uuid.parse(it) },
            status = status,
            createdAt = Instant.fromEpochMilliseconds(createdAt),
            updatedAt = Instant.fromEpochMilliseconds(updatedAt)
        )
    }
}
