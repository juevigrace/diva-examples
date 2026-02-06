package com.diva.collection.database.playlist

import com.diva.database.DivaDB
import com.diva.database.collection.playlist.PlaylistStorage
import com.diva.models.collection.playlist.Playlist
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

class PlaylistStorageImpl(
    private val db: DivaDatabase<DivaDB>
) : PlaylistStorage {
    override suspend fun count(): DivaResult<Long, DivaError> {
        return db.use {
            val value: Long = playlistSuggestionsQueries.count().executeAsOne()
            DivaResult.success(value)
        }
    }

    override suspend fun getAll(
        limit: Int,
        offset: Int
    ): DivaResult<List<Playlist>, DivaError> {
        return db.getList { playlistSuggestionsQueries.findAll(limit.toLong(), offset.toLong(), mapper = ::mapToEntity) }
    }

    override fun getAllFlow(
        limit: Int,
        offset: Int
    ): Flow<DivaResult<List<Playlist>, DivaError>> {
        return db.getListAsFlow { playlistSuggestionsQueries.findAll(limit.toLong(), offset.toLong(), mapper = ::mapToEntity) }
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun getById(id: Uuid): DivaResult<Option<Playlist>, DivaError> {
        return db.getOne { playlistSuggestionsQueries.findOneById(id.toJavaUuid(), mapper = ::mapToEntity) }
    }

    @OptIn(ExperimentalUuidApi::class)
    override fun getByIdFlow(id: Uuid): Flow<DivaResult<Option<Playlist>, DivaError>> {
        return db.getOneAsFlow { playlistSuggestionsQueries.findOneById(id.toJavaUuid(), mapper = ::mapToEntity) }
    }

    @OptIn(ExperimentalTime::class, ExperimentalUuidApi::class)
    override suspend fun insert(item: Playlist): DivaResult<Unit, DivaError> {
        return db.use {
            val rows: Long = transactionWithResult {
                playlistSuggestionsQueries.insert(
                    id = item.id.toJavaUuid(),
                    name = item.name,
                    description = item.description,
                    collection_id = item.collectionId.toJavaUuid(),
                    created_by = item.createdBy.toJavaUuid(),
                    status = item.status,
                )
            }
            if (rows.toInt() == 0) {
                return@use DivaResult.failure(
                    DivaError(
                        ErrorCause.Database.NoRowsAffected(
                            action = DatabaseAction.INSERT,
                            table = Option.Some("diva_playlist_suggestions"),
                            details = Option.Some("Failed to insert")
                        )
                    )
                )
            }
            DivaResult.success(Unit)
        }
    }

    @OptIn(ExperimentalTime::class, ExperimentalUuidApi::class)
    override suspend fun update(item: Playlist): DivaResult<Unit, DivaError> {
        return db.use {
            val rows: Long = transactionWithResult {
                playlistSuggestionsQueries.update(
                    name = item.name,
                    description = item.description,
                    collection_id = item.collectionId.toJavaUuid(),
                    created_by = item.createdBy.toJavaUuid(),
                    status = item.status,
                    id = item.id.toJavaUuid()
                )
            }
            if (rows.toInt() == 0) {
                return@use DivaResult.failure(
                    DivaError(
                        ErrorCause.Database.NoRowsAffected(
                            action = DatabaseAction.UPDATE,
                            table = Option.Some("diva_playlist_suggestions"),
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
                playlistSuggestionsQueries.delete(id.toJavaUuid())
            }
            if (rows.toInt() == 0) {
                return@use DivaResult.failure(
                    DivaError(
                        ErrorCause.Database.NoRowsAffected(
                            action = DatabaseAction.DELETE,
                            table = Option.Some("diva_playlist_suggestions"),
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
        name: String,
        description: String,
        collectionId: UUID,
        createdBy: UUID,
        status: String,
        createdAt: OffsetDateTime,
        updatedAt: OffsetDateTime,
        deletedAt: OffsetDateTime?,
        cEmail: String?,
        cUsername: String?,
        cCreatedAt: OffsetDateTime?,
        cUpdatedAt: OffsetDateTime?,
    ): Playlist {
        require(cEmail != null) { "Creator email cannot be null" }
        require(cUsername != null) { "Creator username cannot be null" }
        require(cCreatedAt != null) { "Creator created at cannot be null" }
        require(cUpdatedAt != null) { "Creator updated at cannot be null" }

        return Playlist(
            id = id.toKotlinUuid(),
            name = name,
            description = description,
            collectionId = collectionId.toKotlinUuid(),
            createdBy = User(
                id = createdBy.toKotlinUuid(),
                email = cEmail,
                username = cUsername,
                createdAt = cCreatedAt.toInstant().toKotlinInstant(),
                updatedAt = cUpdatedAt.toInstant().toKotlinInstant()
            ),
            status = status,
            createdAt = createdAt.toInstant().toKotlinInstant(),
            updatedAt = updatedAt.toInstant().toKotlinInstant(),
            deletedAt = Option.of(deletedAt?.toInstant()?.toKotlinInstant()),
        )
    }
}
