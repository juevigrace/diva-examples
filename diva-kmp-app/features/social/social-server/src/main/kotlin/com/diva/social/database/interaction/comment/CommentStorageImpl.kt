package com.diva.social.database.interaction.comment

import com.diva.database.DivaDB
import com.diva.database.social.comment.CommentStorage
import com.diva.models.social.interaction.comment.Comment
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

class CommentStorageImpl(
    private val db: DivaDatabase<DivaDB>
) : CommentStorage {
    override suspend fun count(): DivaResult<Long, DivaError> {
        return db.use {
            val value: Long = messageQueries.count().executeAsOne()
            DivaResult.success(value)
        }
    }

    override suspend fun getAll(
        limit: Int,
        offset: Int
    ): DivaResult<List<Comment>, DivaError> {
        return db.getList { messageQueries.findAll(limit.toLong(), offset.toLong(), mapper = ::mapToEntity) }
    }

    override fun getAllFlow(
        limit: Int,
        offset: Int
    ): Flow<DivaResult<List<Comment>, DivaError>> {
        return db.getListAsFlow { messageQueries.findAll(limit.toLong(), offset.toLong(), mapper = ::mapToEntity) }
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun getById(id: Uuid): DivaResult<Option<Comment>, DivaError> {
        return db.getOne { messageQueries.findOneById(id.toJavaUuid(), mapper = ::mapToEntity) }
    }

    @OptIn(ExperimentalUuidApi::class)
    override fun getByIdFlow(id: Uuid): Flow<DivaResult<Option<Comment>, DivaError>> {
        return db.getOneAsFlow { messageQueries.findOneById(id.toJavaUuid(), mapper = ::mapToEntity) }
    }

    @OptIn(ExperimentalTime::class, ExperimentalUuidApi::class)
    override suspend fun insert(item: Comment): DivaResult<Unit, DivaError> {
        return db.use {
            val rows: Long = transactionWithResult {
                messageQueries.insert(
                    id = item.id.toJavaUuid(),
                    content = item.content,
                    message_type = item.messageType,
                    chat_id = item.chatId.toJavaUuid(),
                    sender_id = item.senderId.toJavaUuid(),
                    parent_id = item.parentId?.toJavaUuid(),
                )
            }
            if (rows.toInt() == 0) {
                return@use DivaResult.failure(
                    DivaError(
                        ErrorCause.Database.NoRowsAffected(
                            action = DatabaseAction.INSERT,
                            table = Option.Some("diva_message"),
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
                messageQueries.update(
                    content = item.content,
                    message_type = item.messageType,
                    chat_id = item.chatId.toJavaUuid(),
                    sender_id = item.senderId.toJavaUuid(),
                    parent_id = item.parentId?.toJavaUuid(),
                    id = item.id.toJavaUuid()
                )
            }
            if (rows.toInt() == 0) {
                return@use DivaResult.failure(
                    DivaError(
                        ErrorCause.Database.NoRowsAffected(
                            action = DatabaseAction.UPDATE,
                            table = Option.Some("diva_message"),
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
                messageQueries.delete(id.toJavaUuid())
            }
            if (rows.toInt() == 0) {
                return@use DivaResult.failure(
                    DivaError(
                        ErrorCause.Database.NoRowsAffected(
                            action = DatabaseAction.DELETE,
                            table = Option.Some("diva_message"),
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
        content: String,
        messageType: String,
        chatId: UUID,
        senderId: UUID,
        parentId: UUID?,
        createdAt: OffsetDateTime,
        updatedAt: OffsetDateTime,
        deletedAt: OffsetDateTime?,
        sEmail: String?,
        sUsername: String?,
        sCreatedAt: OffsetDateTime?,
        sUpdatedAt: OffsetDateTime?,
    ): Comment {
        require(sEmail != null) { "Sender email cannot be null" }
        require(sUsername != null) { "Sender username cannot be null" }
        require(sCreatedAt != null) { "Sender created at cannot be null" }
        require(sUpdatedAt != null) { "Sender updated at cannot be null" }

        return Comment(
            id = id.toKotlinUuid(),
            content = content,
            messageType = messageType,
            chatId = chatId.toKotlinUuid(),
            senderId = User(
                id = senderId.toKotlinUuid(),
                email = sEmail,
                username = sUsername,
                createdAt = sCreatedAt.toInstant().toKotlinInstant(),
                updatedAt = sUpdatedAt.toInstant().toKotlinInstant()
            ),
            parentId = Option.of(parentId?.toKotlinUuid()),
            createdAt = createdAt.toInstant().toKotlinInstant(),
            updatedAt = updatedAt.toInstant().toKotlinInstant(),
            deletedAt = Option.of(deletedAt?.toInstant()?.toKotlinInstant()),
        )
    }
}
