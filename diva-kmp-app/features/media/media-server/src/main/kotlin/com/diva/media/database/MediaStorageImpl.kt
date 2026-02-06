package com.diva.media.database

import com.diva.database.DivaDB
import com.diva.database.media.MediaStorage
import com.diva.models.media.Media
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

class MediaStorageImpl(
    private val db: DivaDatabase<DivaDB>
) : MediaStorage {
    override suspend fun count(): DivaResult<Long, DivaError> {
        return db.use {
            val value: Long = mediaQueries.count().executeAsOne()
            DivaResult.success(value)
        }
    }

    override suspend fun getAll(
        limit: Int,
        offset: Int
    ): DivaResult<List<Media>, DivaError> {
        return db.getList { mediaQueries.findAll(limit.toLong(), offset.toLong(), mapper = ::mapToEntity) }
    }

    override fun getAllFlow(
        limit: Int,
        offset: Int
    ): Flow<DivaResult<List<Media>, DivaError>> {
        return db.getListAsFlow { mediaQueries.findAll(limit.toLong(), offset.toLong(), mapper = ::mapToEntity) }
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun getById(id: Uuid): DivaResult<Option<Media>, DivaError> {
        return db.getOne { mediaQueries.findOneById(id.toJavaUuid(), mapper = ::mapToEntity) }
    }

    @OptIn(ExperimentalUuidApi::class)
    override fun getByIdFlow(id: Uuid): Flow<DivaResult<Option<Media>, DivaError>> {
        return db.getOneAsFlow { mediaQueries.findOneById(id.toJavaUuid(), mapper = ::mapToEntity) }
    }

    @OptIn(ExperimentalTime::class, ExperimentalUuidApi::class)
    override suspend fun insert(item: Media): DivaResult<Unit, DivaError> {
        return db.use {
            val rows: Long = transactionWithResult {
                mediaQueries.insert(
                    id = item.id.toJavaUuid(),
                    title = item.title,
                    description = item.description,
                    file_path = item.filePath,
                    file_size = item.fileSize,
                    mime_type = item.mimeType,
                    duration = item.duration,
                    thumbnail_path = item.thumbnailPath,
                    media_type = item.mediaType,
                    visibility = item.visibility,
                    tags = item.tags,
                    metadata = item.metadata,
                    owner_id = item.ownerId.toJavaUuid(),
                )
            }
            if (rows.toInt() == 0) {
                return@use DivaResult.failure(
                    DivaError(
                        ErrorCause.Database.NoRowsAffected(
                            action = DatabaseAction.INSERT,
                            table = Option.Some("diva_media"),
                            details = Option.Some("Failed to insert")
                        )
                    )
                )
            }
            DivaResult.success(Unit)
        }
    }

    @OptIn(ExperimentalTime::class, ExperimentalUuidApi::class)
    override suspend fun update(item: Media): DivaResult<Unit, DivaError> {
        return db.use {
            val rows: Long = transactionWithResult {
                mediaQueries.update(
                    title = item.title,
                    description = item.description,
                    file_path = item.filePath,
                    file_size = item.fileSize,
                    mime_type = item.mimeType,
                    duration = item.duration,
                    thumbnail_path = item.thumbnailPath,
                    media_type = item.mediaType,
                    visibility = item.visibility,
                    tags = item.tags,
                    metadata = item.metadata,
                    owner_id = item.ownerId.toJavaUuid(),
                    id = item.id.toJavaUuid()
                )
            }
            if (rows.toInt() == 0) {
                return@use DivaResult.failure(
                    DivaError(
                        ErrorCause.Database.NoRowsAffected(
                            action = DatabaseAction.UPDATE,
                            table = Option.Some("diva_media"),
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
                mediaQueries.delete(id.toJavaUuid())
            }
            if (rows.toInt() == 0) {
                return@use DivaResult.failure(
                    DivaError(
                        ErrorCause.Database.NoRowsAffected(
                            action = DatabaseAction.DELETE,
                            table = Option.Some("diva_media"),
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
        description: String,
        filePath: String,
        fileSize: Long,
        mimeType: String,
        duration: Long?,
        thumbnailPath: String?,
        mediaType: String,
        visibility: String,
        tags: List<String>?,
        metadata: String?,
        ownerId: UUID,
        createdAt: OffsetDateTime,
        updatedAt: OffsetDateTime,
        deletedAt: OffsetDateTime?,
        oEmail: String?,
        oUsername: String?,
        oCreatedAt: OffsetDateTime?,
        oUpdatedAt: OffsetDateTime?,
    ): Media {
        require(oEmail != null) { "Owner email cannot be null" }
        require(oUsername != null) { "Owner username cannot be null" }
        require(oCreatedAt != null) { "Owner created at cannot be null" }
        require(oUpdatedAt != null) { "Owner updated at cannot be null" }

        return Media(
            id = id.toKotlinUuid(),
            title = title,
            description = description,
            filePath = filePath,
            fileSize = fileSize,
            mimeType = mimeType,
            duration = duration,
            thumbnailPath = thumbnailPath,
            mediaType = mediaType,
            visibility = visibility,
            tags = tags ?: emptyList(),
            metadata = metadata,
            ownerId = User(
                id = ownerId.toKotlinUuid(),
                email = oEmail,
                username = oUsername,
                createdAt = oCreatedAt.toInstant().toKotlinInstant(),
                updatedAt = oUpdatedAt.toInstant().toKotlinInstant()
            ),
            createdAt = createdAt.toInstant().toKotlinInstant(),
            updatedAt = updatedAt.toInstant().toKotlinInstant(),
            deletedAt = Option.of(deletedAt?.toInstant()?.toKotlinInstant()),
        )
    }
}
