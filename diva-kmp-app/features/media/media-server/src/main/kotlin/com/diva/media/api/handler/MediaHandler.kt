package com.diva.media.api.handler

import com.diva.models.api.ApiResponse
import com.diva.models.api.PaginationResponse
import com.diva.models.api.media.dtos.CreateMediaDto
import com.diva.models.api.media.dtos.UpdateMediaDto
import com.diva.models.api.media.response.MediaResponse
import io.github.juevigrace.diva.core.DivaResult
import io.github.juevigrace.diva.core.errors.DivaError
import kotlin.uuid.ExperimentalUuidApi

interface MediaHandler {

    suspend fun getMedia(
        page: Int,
        pageSize: Int
    ): DivaResult<ApiResponse<PaginationResponse<MediaResponse>>, DivaError>

    @OptIn(ExperimentalUuidApi::class)
    suspend fun getMedia(id: String): DivaResult<ApiResponse<MediaResponse>, DivaError>

    suspend fun createMedia(dto: CreateMediaDto): DivaResult<ApiResponse<MediaResponse>, DivaError>

    @OptIn(ExperimentalUuidApi::class)
    suspend fun updateMedia(
        id: String,
        dto: UpdateMediaDto
    ): DivaResult<ApiResponse<MediaResponse>, DivaError>

    @OptIn(ExperimentalUuidApi::class)
    suspend fun deleteMedia(id: String): DivaResult<ApiResponse<Unit>, DivaError>
}

class MediaHandlerImpl(
    private val service: com.diva.media.data.MediaService
) : MediaHandler {
    override suspend fun getMedia(
        page: Int,
        pageSize: Int
    ): DivaResult<ApiResponse<PaginationResponse<MediaResponse>>, DivaError> {
        // TODO: Implement when service has methods
        return DivaResult.failure(
            DivaError(
                cause = io.github.juevigrace.diva.core.errors.ErrorCause.Network.Error(
                    method = io.github.juevigrace.diva.core.network.HttpRequestMethod.GET,
                    url = "/api/media",
                    status = io.github.juevigrace.diva.core.network.HttpStatusCodes.NotImplemented,
                    details = io.github.juevigrace.diva.core.Option.Some("Not yet implemented")
                )
            )
        )
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun getMedia(id: String): DivaResult<ApiResponse<MediaResponse>, DivaError> {
        // TODO: Implement when service has methods
        return DivaResult.failure(
            DivaError(
                cause = io.github.juevigrace.diva.core.errors.ErrorCause.Network.Error(
                    method = io.github.juevigrace.diva.core.network.HttpRequestMethod.GET,
                    url = "/api/media/$id",
                    status = io.github.juevigrace.diva.core.network.HttpStatusCodes.NotImplemented,
                    details = io.github.juevigrace.diva.core.Option.Some("Not yet implemented")
                )
            )
        )
    }

    override suspend fun createMedia(dto: CreateMediaDto): DivaResult<ApiResponse<MediaResponse>, DivaError> {
        // TODO: Implement when service has methods
        return DivaResult.failure(
            DivaError(
                cause = io.github.juevigrace.diva.core.errors.ErrorCause.Network.Error(
                    method = io.github.juevigrace.diva.core.network.HttpRequestMethod.POST,
                    url = "/api/media",
                    status = io.github.juevigrace.diva.core.network.HttpStatusCodes.NotImplemented,
                    details = io.github.juevigrace.diva.core.Option.Some("Not yet implemented")
                )
            )
        )
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun updateMedia(
        id: String,
        dto: UpdateMediaDto
    ): DivaResult<ApiResponse<MediaResponse>, DivaError> {
        // TODO: Implement when service has methods
        return DivaResult.failure(
            DivaError(
                cause = io.github.juevigrace.diva.core.errors.ErrorCause.Network.Error(
                    method = io.github.juevigrace.diva.core.network.HttpRequestMethod.PUT,
                    url = "/api/media/$id",
                    status = io.github.juevigrace.diva.core.network.HttpStatusCodes.NotImplemented,
                    details = io.github.juevigrace.diva.core.Option.Some("Not yet implemented")
                )
            )
        )
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun deleteMedia(id: String): DivaResult<ApiResponse<Unit>, DivaError> {
        // TODO: Implement when service has methods
        return DivaResult.failure(
            DivaError(
                cause = io.github.juevigrace.diva.core.errors.ErrorCause.Network.Error(
                    method = io.github.juevigrace.diva.core.network.HttpRequestMethod.DELETE,
                    url = "/api/media/$id",
                    status = io.github.juevigrace.diva.core.network.HttpStatusCodes.NotImplemented,
                    details = io.github.juevigrace.diva.core.Option.Some("Not yet implemented")
                )
            )
        )
    }
}
