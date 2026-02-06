package com.diva.media.database

import com.diva.database.DivaDB
import com.diva.database.media.MediaStorage
import com.diva.models.media.Media
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
        return db.getOne { mediaQueries.findOneById(id.toString(), mapper = ::mapToEntity) }
    }

    @OptIn(ExperimentalUuidApi::class)
    override fun getByIdFlow(id: Uuid): Flow<DivaResult<Option<Media>, DivaError>> {
        return db.getOneAsFlow { mediaQueries.findOneById(id.toString(), mapper = ::mapToEntity) }
    }

    @OptIn(ExperimentalTime::class, ExperimentalUuidApi::class)
    override suspend fun insert(item: Media): DivaResult<Unit, DivaError> {
        return db.use {
            val rows: Long = transactionWithResult {
                mediaQueries.insert(
                    id = item.id.toString(),
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
                    owner_id = item.ownerId.toString(),
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
                    owner_id = item.ownerId.toString(),
                    id = item.id.toString()
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
                mediaQueries.delete(id.toString())
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
        id: String,
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
        ownerId: String,
        createdAt: Long,
        updatedAt: Long,
        deletedAt: Long?,
    ): Media {
        return Media(
            id = Uuid.parse(id),
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
            ownerId = Uuid.parse(ownerId),
            createdAt = Instant.fromEpochMilliseconds(createdAt),
            updatedAt = Instant.fromEpochMilliseconds(updatedAt),
            deletedAt = Option.of(deletedAt?.let { Instant.fromEpochMilliseconds(it) }),
        )
    }
}