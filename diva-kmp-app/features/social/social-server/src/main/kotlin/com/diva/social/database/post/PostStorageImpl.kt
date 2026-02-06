package com.diva.social.database.post

import com.diva.database.DivaDB
import com.diva.database.social.post.PostStorage
import com.diva.models.social.post.Post
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
        return db.getOne { postQueries.findOneById(id.toJavaUuid(), mapper = ::mapToEntity) }
    }

    @OptIn(ExperimentalUuidApi::class)
    override fun getByIdFlow(id: Uuid): Flow<DivaResult<Option<Post>, DivaError>> {
        return db.getOneAsFlow { postQueries.findOneById(id.toJavaUuid(), mapper = ::mapToEntity) }
    }

    @OptIn(ExperimentalTime::class, ExperimentalUuidApi::class)
    override suspend fun insert(item: Post): DivaResult<Unit, DivaError> {
        return db.use {
            val rows: Long = transactionWithResult {
                postQueries.insert(
                    id = item.id.toJavaUuid(),
                    title = item.title,
                    content = item.content,
                    visibility = item.visibility,
                    author_id = item.authorId.toJavaUuid(),
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
                    visibility = item.visibility,
                    author_id = item.authorId.toJavaUuid(),
                    id = item.id.toJavaUuid()
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
                postQueries.delete(id.toJavaUuid())
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
        id: UUID,
        title: String,
        content: String,
        visibility: String,
        authorId: UUID,
        createdAt: OffsetDateTime,
        updatedAt: OffsetDateTime,
        deletedAt: OffsetDateTime?,
        aEmail: String?,
        aUsername: String?,
        aCreatedAt: OffsetDateTime?,
        aUpdatedAt: OffsetDateTime?,
    ): Post {
        require(aEmail != null) { "Author email cannot be null" }
        require(aUsername != null) { "Author username cannot be null" }
        require(aCreatedAt != null) { "Author created at cannot be null" }
        require(aUpdatedAt != null) { "Author updated at cannot be null" }

        return Post(
            id = id.toKotlinUuid(),
            title = title,
            content = content,
            visibility = visibility,
            authorId = User(
                id = authorId.toKotlinUuid(),
                email = aEmail,
                username = aUsername,
                createdAt = aCreatedAt.toInstant().toKotlinInstant(),
                updatedAt = aUpdatedAt.toInstant().toKotlinInstant()
            ),
            createdAt = createdAt.toInstant().toKotlinInstant(),
            updatedAt = updatedAt.toInstant().toKotlinInstant(),
            deletedAt = Option.of(deletedAt?.toInstant()?.toKotlinInstant()),
        )
    }
}
