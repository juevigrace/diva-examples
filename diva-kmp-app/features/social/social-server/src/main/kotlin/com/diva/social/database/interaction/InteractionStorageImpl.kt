package com.diva.social.database.interaction

import com.diva.database.DivaDB
import com.diva.database.social.interaction.InteractionStorage
import com.diva.models.social.interaction.Interaction
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
        return db.getOne { interactionQueries.findOneById(id.toJavaUuid(), mapper = ::mapToEntity) }
    }

    @OptIn(ExperimentalUuidApi::class)
    override fun getByIdFlow(id: Uuid): Flow<DivaResult<Option<Interaction>, DivaError>> {
        return db.getOneAsFlow { interactionQueries.findOneById(id.toJavaUuid(), mapper = ::mapToEntity) }
    }

    @OptIn(ExperimentalTime::class, ExperimentalUuidApi::class)
    override suspend fun insert(item: Interaction): DivaResult<Unit, DivaError> {
        return db.use {
            val rows: Long = transactionWithResult {
                interactionQueries.insert(
                    id = item.id.toJavaUuid(),
                    target_id = item.targetId.toJavaUuid(),
                    target_type = item.targetType,
                    user_id = item.userId.toJavaUuid(),
                    reaction_type = item.reactionType,
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
                    target_id = item.targetId.toJavaUuid(),
                    target_type = item.targetType,
                    user_id = item.userId.toJavaUuid(),
                    reaction_type = item.reactionType,
                    id = item.id.toJavaUuid()
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
                interactionQueries.delete(id.toJavaUuid())
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
        id: UUID,
        targetId: UUID,
        targetType: String,
        userId: UUID,
        reactionType: String,
        createdAt: OffsetDateTime,
        updatedAt: OffsetDateTime,
        deletedAt: OffsetDateTime?,
        uEmail: String?,
        uUsername: String?,
        uCreatedAt: OffsetDateTime?,
        uUpdatedAt: OffsetDateTime?,
    ): Interaction {
        require(uEmail != null) { "User email cannot be null" }
        require(uUsername != null) { "User username cannot be null" }
        require(uCreatedAt != null) { "User created at cannot be null" }
        require(uUpdatedAt != null) { "User updated at cannot be null" }

        return Interaction(
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
            reactionType = reactionType,
            createdAt = createdAt.toInstant().toKotlinInstant(),
            updatedAt = updatedAt.toInstant().toKotlinInstant(),
            deletedAt = Option.of(deletedAt?.toInstant()?.toKotlinInstant()),
        )
    }
}
