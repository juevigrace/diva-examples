package com.diva.media.api.handler

import com.diva.models.api.ApiResponse
import com.diva.models.api.PaginationResponse
import com.diva.models.api.media.tag.dtos.CreateTagDto
import com.diva.models.api.media.tag.dtos.UpdateTagDto
import com.diva.models.api.media.tag.response.TagResponse
import io.github.juevigrace.diva.core.DivaResult
import io.github.juevigrace.diva.core.errors.DivaError
import kotlin.uuid.ExperimentalUuidApi

interface TagHandler {

    suspend fun getTags(
        page: Int,
        pageSize: Int
    ): DivaResult<ApiResponse<PaginationResponse<TagResponse>>, DivaError>

    @OptIn(ExperimentalUuidApi::class)
    suspend fun getTag(id: String): DivaResult<ApiResponse<TagResponse>, DivaError>

    suspend fun createTag(dto: CreateTagDto): DivaResult<ApiResponse<TagResponse>, DivaError>

    @OptIn(ExperimentalUuidApi::class)
    suspend fun updateTag(
        id: String,
        dto: UpdateTagDto
    ): DivaResult<ApiResponse<TagResponse>, DivaError>

    @OptIn(ExperimentalUuidApi::class)
    suspend fun deleteTag(id: String): DivaResult<ApiResponse<Unit>, DivaError>
}

class TagHandlerImpl(
    private val service: com.diva.media.data.TagService
) : TagHandler {
    override suspend fun getTags(
        page: Int,
        pageSize: Int
    ): DivaResult<ApiResponse<PaginationResponse<TagResponse>>, DivaError> {
        // TODO: Implement when service has methods
        return DivaResult.failure(
            DivaError(
                cause = io.github.juevigrace.diva.core.errors.ErrorCause.Network.Error(
                    method = io.github.juevigrace.diva.core.network.HttpRequestMethod.GET,
                    url = "/api/tag",
                    status = io.github.juevigrace.diva.core.network.HttpStatusCodes.NotImplemented,
                    details = io.github.juevigrace.diva.core.Option.Some("Not yet implemented")
                )
            )
        )
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun getTag(id: String): DivaResult<ApiResponse<TagResponse>, DivaError> {
        // TODO: Implement when service has methods
        return DivaResult.failure(
            DivaError(
                cause = io.github.juevigrace.diva.core.errors.ErrorCause.Network.Error(
                    method = io.github.juevigrace.diva.core.network.HttpRequestMethod.GET,
                    url = "/api/tag/$id",
                    status = io.github.juevigrace.diva.core.network.HttpStatusCodes.NotImplemented,
                    details = io.github.juevigrace.diva.core.Option.Some("Not yet implemented")
                )
            )
        )
    }

    override suspend fun createTag(dto: CreateTagDto): DivaResult<ApiResponse<TagResponse>, DivaError> {
        // TODO: Implement when service has methods
        return DivaResult.failure(
            DivaError(
                cause = io.github.juevigrace.diva.core.errors.ErrorCause.Network.Error(
                    method = io.github.juevigrace.diva.core.network.HttpRequestMethod.POST,
                    url = "/api/tag",
                    status = io.github.juevigrace.diva.core.network.HttpStatusCodes.NotImplemented,
                    details = io.github.juevigrace.diva.core.Option.Some("Not yet implemented")
                )
            )
        )
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun updateTag(
        id: String,
        dto: UpdateTagDto
    ): DivaResult<ApiResponse<TagResponse>, DivaError> {
        // TODO: Implement when service has methods
        return DivaResult.failure(
            DivaError(
                cause = io.github.juevigrace.diva.core.errors.ErrorCause.Network.Error(
                    method = io.github.juevigrace.diva.core.network.HttpRequestMethod.PUT,
                    url = "/api/tag/$id",
                    status = io.github.juevigrace.diva.core.network.HttpStatusCodes.NotImplemented,
                    details = io.github.juevigrace.diva.core.Option.Some("Not yet implemented")
                )
            )
        )
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun deleteTag(id: String): DivaResult<ApiResponse<Unit>, DivaError> {
        // TODO: Implement when service has methods
        return DivaResult.failure(
            DivaError(
                cause = io.github.juevigrace.diva.core.errors.ErrorCause.Network.Error(
                    method = io.github.juevigrace.diva.core.network.HttpRequestMethod.DELETE,
                    url = "/api/tag/$id",
                    status = io.github.juevigrace.diva.core.network.HttpStatusCodes.NotImplemented,
                    details = io.github.juevigrace.diva.core.Option.Some("Not yet implemented")
                )
            )
        )
    }
}
