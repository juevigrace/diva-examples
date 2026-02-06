package com.diva.social.api.handler

import com.diva.models.api.ApiResponse
import com.diva.models.api.PaginationResponse
import com.diva.models.api.social.post.dtos.CreatePostDto
import com.diva.models.api.social.post.dtos.UpdatePostDto
import com.diva.models.api.social.post.response.PostResponse
import io.github.juevigrace.diva.core.DivaResult
import io.github.juevigrace.diva.core.errors.DivaError
import kotlin.uuid.ExperimentalUuidApi

interface PostHandler {

    suspend fun getPosts(
        page: Int,
        pageSize: Int
    ): DivaResult<ApiResponse<PaginationResponse<PostResponse>>, DivaError>

    @OptIn(ExperimentalUuidApi::class)
    suspend fun getPost(id: String): DivaResult<ApiResponse<PostResponse>, DivaError>

    suspend fun createPost(dto: CreatePostDto): DivaResult<ApiResponse<PostResponse>, DivaError>

    @OptIn(ExperimentalUuidApi::class)
    suspend fun updatePost(
        id: String,
        dto: UpdatePostDto
    ): DivaResult<ApiResponse<PostResponse>, DivaError>

    @OptIn(ExperimentalUuidApi::class)
    suspend fun deletePost(id: String): DivaResult<ApiResponse<Unit>, DivaError>
}

class PostHandlerImpl(
    private val service: com.diva.social.data.PostService
) : PostHandler {
    override suspend fun getPosts(
        page: Int,
        pageSize: Int
    ): DivaResult<ApiResponse<PaginationResponse<PostResponse>>, DivaError> {
        // TODO: Implement when service has methods
        return DivaResult.failure(
            DivaError(
                cause = io.github.juevigrace.diva.core.errors.ErrorCause.Network.Error(
                    method = io.github.juevigrace.diva.core.network.HttpRequestMethod.GET,
                    url = "/api/post",
                    status = io.github.juevigrace.diva.core.network.HttpStatusCodes.NotImplemented,
                    details = io.github.juevigrace.diva.core.Option.Some("Not yet implemented")
                )
            )
        )
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun getPost(id: String): DivaResult<ApiResponse<PostResponse>, DivaError> {
        // TODO: Implement when service has methods
        return DivaResult.failure(
            DivaError(
                cause = io.github.juevigrace.diva.core.errors.ErrorCause.Network.Error(
                    method = io.github.juevigrace.diva.core.network.HttpRequestMethod.GET,
                    url = "/api/post/$id",
                    status = io.github.juevigrace.diva.core.network.HttpStatusCodes.NotImplemented,
                    details = io.github.juevigrace.diva.core.Option.Some("Not yet implemented")
                )
            )
        )
    }

    override suspend fun createPost(dto: CreatePostDto): DivaResult<ApiResponse<PostResponse>, DivaError> {
        // TODO: Implement when service has methods
        return DivaResult.failure(
            DivaError(
                cause = io.github.juevigrace.diva.core.errors.ErrorCause.Network.Error(
                    method = io.github.juevigrace.diva.core.network.HttpRequestMethod.POST,
                    url = "/api/post",
                    status = io.github.juevigrace.diva.core.network.HttpStatusCodes.NotImplemented,
                    details = io.github.juevigrace.diva.core.Option.Some("Not yet implemented")
                )
            )
        )
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun updatePost(
        id: String,
        dto: UpdatePostDto
    ): DivaResult<ApiResponse<PostResponse>, DivaError> {
        // TODO: Implement when service has methods
        return DivaResult.failure(
            DivaError(
                cause = io.github.juevigrace.diva.core.errors.ErrorCause.Network.Error(
                    method = io.github.juevigrace.diva.core.network.HttpRequestMethod.PUT,
                    url = "/api/post/$id",
                    status = io.github.juevigrace.diva.core.network.HttpStatusCodes.NotImplemented,
                    details = io.github.juevigrace.diva.core.Option.Some("Not yet implemented")
                )
            )
        )
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun deletePost(id: String): DivaResult<ApiResponse<Unit>, DivaError> {
        // TODO: Implement when service has methods
        return DivaResult.failure(
            DivaError(
                cause = io.github.juevigrace.diva.core.errors.ErrorCause.Network.Error(
                    method = io.github.juevigrace.diva.core.network.HttpRequestMethod.DELETE,
                    url = "/api/post/$id",
                    status = io.github.juevigrace.diva.core.network.HttpStatusCodes.NotImplemented,
                    details = io.github.juevigrace.diva.core.Option.Some("Not yet implemented")
                )
            )
        )
    }
}
