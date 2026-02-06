package com.diva.media.api.handler

import com.diva.models.api.ApiResponse
import com.diva.models.api.PaginationResponse
import com.diva.models.api.media.tag.dtos.CreateMediaTagDto
import com.diva.models.api.media.tag.dtos.UpdateMediaTagDto
import com.diva.models.api.media.tag.response.MediaTagResponse
import io.github.juevigrace.diva.core.DivaResult
import io.github.juevigrace.diva.core.errors.DivaError
import kotlin.uuid.ExperimentalUuidApi

interface MediaTagHandler {

    suspend fun getMediaTags(
        page: Int,
        pageSize: Int
    ): DivaResult<ApiResponse<PaginationResponse<MediaTagResponse>>, DivaError>

    @OptIn(ExperimentalUuidApi::class)
    suspend fun getMediaTag(id: String): DivaResult<ApiResponse<MediaTagResponse>, DivaError>

    suspend fun createMediaTag(dto: CreateMediaTagDto): DivaResult<ApiResponse<MediaTagResponse>, DivaError>

    @OptIn(ExperimentalUuidApi::class)
    suspend fun updateMediaTag(
        id: String,
        dto: UpdateMediaTagDto
    ): DivaResult<ApiResponse<MediaTagResponse>, DivaError>

    @OptIn(ExperimentalUuidApi::class)
    suspend fun deleteMediaTag(id: String): DivaResult<ApiResponse<Unit>, DivaError>
}

class MediaTagHandlerImpl(
    private val service: com.diva.media.data.MediaTagService
) : MediaTagHandler {
    override suspend fun getMediaTags(
        page: Int,
        pageSize: Int
    ): DivaResult<ApiResponse<PaginationResponse<MediaTagResponse>>, DivaError> {
        // TODO: Implement when service has methods
        return DivaResult.failure(
            DivaError(
                cause = io.github.juevigrace.diva.core.errors.ErrorCause.Network.Error(
                    method = io.github.juevigrace.diva.core.network.HttpRequestMethod.GET,
                    url = "/api/media/tag",
                    status = io.github.juevigrace.diva.core.network.HttpStatusCodes.NotImplemented,
                    details = io.github.juevigrace.diva.core.Option.Some("Not yet implemented")
                )
            )
        )
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun getMediaTag(id: String): DivaResult<ApiResponse<MediaTagResponse>, DivaError> {
        // TODO: Implement when service has methods
        return DivaResult.failure(
            DivaError(
                cause = io.github.juevigrace.diva.core.errors.ErrorCause.Network.Error(
                    method = io.github.juevigrace.diva.core.network.HttpRequestMethod.GET,
                    url = "/api/media/tag/$id",
                    status = io.github.juevigrace.diva.core.network.HttpStatusCodes.NotImplemented,
                    details = io.github.juevigrace.diva.core.Option.Some("Not yet implemented")
                )
            )
        )
    }

    override suspend fun createMediaTag(dto: CreateMediaTagDto): DivaResult<ApiResponse<MediaTagResponse>, DivaError> {
        // TODO: Implement when service has methods
        return DivaResult.failure(
            DivaError(
                cause = io.github.juevigrace.diva.core.errors.ErrorCause.Network.Error(
                    method = io.github.juevigrace.diva.core.network.HttpRequestMethod.POST,
                    url = "/api/media/tag",
                    status = io.github.juevigrace.diva.core.network.HttpStatusCodes.NotImplemented,
                    details = io.github.juevigrace.diva.core.Option.Some("Not yet implemented")
                )
            )
        )
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun updateMediaTag(
        id: String,
        dto: UpdateMediaTagDto
    ): DivaResult<ApiResponse<MediaTagResponse>, DivaError> {
        // TODO: Implement when service has methods
        return DivaResult.failure(
            DivaError(
                cause = io.github.juevigrace.diva.core.errors.ErrorCause.Network.Error(
                    method = io.github.juevigrace.diva.core.network.HttpRequestMethod.PUT,
                    url = "/api/media/tag/$id",
                    status = io.github.juevigrace.diva.core.network.HttpStatusCodes.NotImplemented,
                    details = io.github.juevigrace.diva.core.Option.Some("Not yet implemented")
                )
            )
        )
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun deleteMediaTag(id: String): DivaResult<ApiResponse<Unit>, DivaError> {
        // TODO: Implement when service has methods
        return DivaResult.failure(
            DivaError(
                cause = io.github.juevigrace.diva.core.errors.ErrorCause.Network.Error(
                    method = io.github.juevigrace.diva.core.network.HttpRequestMethod.DELETE,
                    url = "/api/media/tag/$id",
                    status = io.github.juevigrace.diva.core.network.HttpStatusCodes.NotImplemented,
                    details = io.github.juevigrace.diva.core.Option.Some("Not yet implemented")
                )
            )
        )
    }
}
