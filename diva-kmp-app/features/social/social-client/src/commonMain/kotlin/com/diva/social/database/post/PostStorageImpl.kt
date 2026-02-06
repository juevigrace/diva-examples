package com.diva.social.database.post

import com.diva.database.DivaDB
import com.diva.database.social.post.PostStorage
import com.diva.models.social.post.Post
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

class PostStorageImpl(
    private val db: DivaDatabase<DivaDB>
) : PostStorage {
    override suspend fun count(): DivaResult<Long, DivaError> {
        return db.use {
            val value: Long = postQueries.count().executeAsOne()
            DivaResult.success(value)
        }
    }

    override suspend fun getAll(
        limit: Int,
        offset: Int
    ): DivaResult<List<Post>, DivaError> {
        return db.getList { postQueries.findAll(limit.toLong(), offset.toLong(), mapper = ::mapToEntity) }
    }

    override fun getAllFlow(
        limit: Int,
        offset: Int
    ): Flow<DivaResult<List<Post>, DivaError>> {
        return db.getListAsFlow { postQueries.findAll(limit.toLong(), offset.toLong(), mapper = ::mapToEntity) }
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun getById(id: Uuid): DivaResult<Option<Post>, DivaError> {
        return db.getOne { postQueries.findOneById(id.toString(), mapper = ::mapToEntity) }
    }

    @OptIn(ExperimentalUuidApi::class)
    override fun getByIdFlow(id: Uuid): Flow<DivaResult<Option<Post>, DivaError>> {
        return db.getOneAsFlow { postQueries.findOneById(id.toString(), mapper = ::mapToEntity) }
    }

    @OptIn(ExperimentalTime::class, ExperimentalUuidApi::class)
    override suspend fun insert(item: Post): DivaResult<Unit, DivaError> {
        return db.use {
            val rows: Long = transactionWithResult {
                postQueries.insert(
                    id = item.id.toString(),
                    title = item.title,
                    content = item.content,
                    author_id = item.authorId.toString(),
                    status = item.status,
                    visibility = item.visibility,
                    created_at = item.createdAt.toEpochMilliseconds(),
                    updated_at = item.updatedAt.toEpochMilliseconds(),
                    published_at = item.publishedAt?.toEpochMilliseconds(),
                )
            }
            if (rows.toInt() == 0) {
                return@use DivaResult.failure(
                    DivaError(
                        ErrorCause.Database.NoRowsAffected(
                            action = DatabaseAction.INSERT,
                            table = Option.Some("diva_post"),
                            details = Option.Some("Failed to insert")
                        )
                    )
                )
            }
            DivaResult.success(Unit)
        }
    }

    @OptIn(ExperimentalTime::class, ExperimentalUuidApi::class)
    override suspend fun update(item: Post): DivaResult<Unit, DivaError> {
        return db.use {
            val rows: Long = transactionWithResult {
                postQueries.update(
                    title = item.title,
                    content = item.content,
                    author_id = item.authorId.toString(),
                    status = item.status,
                    visibility = item.visibility,
                    created_at = item.createdAt.toEpochMilliseconds(),
                    updated_at = item.updatedAt.toEpochMilliseconds(),
                    published_at = item.publishedAt?.toEpochMilliseconds(),
                    id = item.id.toString()
                )
            }
            if (rows.toInt() == 0) {
                return@use DivaResult.failure(
                    DivaError(
                        ErrorCause.Database.NoRowsAffected(
                            action = DatabaseAction.UPDATE,
                            table = Option.Some("diva_post"),
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
                postQueries.delete(id.toString())
            }
            if (rows.toInt() == 0) {
                return@use DivaResult.failure(
                    DivaError(
                        ErrorCause.Database.NoRowsAffected(
                            action = DatabaseAction.DELETE,
                            table = Option.Some("diva_post"),
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
        content: String,
        authorId: String,
        status: String,
        visibility: String,
        createdAt: Long,
        updatedAt: Long,
        publishedAt: Long?
    ): Post {
        return Post(
            id = Uuid.parse(id),
            title = title,
            content = content,
            authorId = Uuid.parse(authorId),
            status = status,
            visibility = visibility,
            createdAt = Instant.fromEpochMilliseconds(createdAt),
            updatedAt = Instant.fromEpochMilliseconds(updatedAt),
            publishedAt = publishedAt?.let { Instant.fromEpochMilliseconds(it) }
        )
    }
}
