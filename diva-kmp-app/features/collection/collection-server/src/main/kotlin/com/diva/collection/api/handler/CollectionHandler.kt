package com.diva.collection.api.handler

import com.diva.models.api.ApiResponse
import com.diva.models.api.PaginationResponse
import com.diva.models.api.collection.dtos.CreateCollectionDto
import com.diva.models.api.collection.dtos.UpdateCollectionDto
import com.diva.models.api.collection.response.CollectionResponse
import io.github.juevigrace.diva.core.DivaResult
import io.github.juevigrace.diva.core.errors.DivaError
import kotlin.uuid.ExperimentalUuidApi

interface CollectionHandler {

    suspend fun getCollections(
        page: Int,
        pageSize: Int
    ): DivaResult<ApiResponse<PaginationResponse<CollectionResponse>>, DivaError>

    @OptIn(ExperimentalUuidApi::class)
    suspend fun getCollection(id: String): DivaResult<ApiResponse<CollectionResponse>, DivaError>

    suspend fun createCollection(dto: CreateCollectionDto): DivaResult<ApiResponse<CollectionResponse>, DivaError>

    @OptIn(ExperimentalUuidApi::class)
    suspend fun updateCollection(
        id: String,
        dto: UpdateCollectionDto
    ): DivaResult<ApiResponse<CollectionResponse>, DivaError>

    @OptIn(ExperimentalUuidApi::class)
    suspend fun deleteCollection(id: String): DivaResult<ApiResponse<Unit>, DivaError>
}

class CollectionHandlerImpl(
    private val service: com.diva.collection.data.CollectionService
) : CollectionHandler {
    override suspend fun getCollections(
        page: Int,
        pageSize: Int
    ): DivaResult<ApiResponse<PaginationResponse<CollectionResponse>>, DivaError> {
        // TODO: Implement when service has methods
        return DivaResult.failure(
            DivaError(
                cause = io.github.juevigrace.diva.core.errors.ErrorCause.Network.Error(
                    method = io.github.juevigrace.diva.core.network.HttpRequestMethod.GET,
                    url = "/api/collection",
                    status = io.github.juevigrace.diva.core.network.HttpStatusCodes.NotImplemented,
                    details = io.github.juevigrace.diva.core.Option.Some("Not yet implemented")
                )
            )
        )
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun getCollection(id: String): DivaResult<ApiResponse<CollectionResponse>, DivaError> {
        // TODO: Implement when service has methods
        return DivaResult.failure(
            DivaError(
                cause = io.github.juevigrace.diva.core.errors.ErrorCause.Network.Error(
                    method = io.github.juevigrace.diva.core.network.HttpRequestMethod.GET,
                    url = "/api/collection/$id",
                    status = io.github.juevigrace.diva.core.network.HttpStatusCodes.NotImplemented,
                    details = io.github.juevigrace.diva.core.Option.Some("Not yet implemented")
                )
            )
        )
    }

    override suspend fun createCollection(dto: CreateCollectionDto): DivaResult<ApiResponse<CollectionResponse>, DivaError> {
        // TODO: Implement when service has methods
        return DivaResult.failure(
            DivaError(
                cause = io.github.juevigrace.diva.core.errors.ErrorCause.Network.Error(
                    method = io.github.juevigrace.diva.core.network.HttpRequestMethod.POST,
                    url = "/api/collection",
                    status = io.github.juevigrace.diva.core.network.HttpStatusCodes.NotImplemented,
                    details = io.github.juevigrace.diva.core.Option.Some("Not yet implemented")
                )
            )
        )
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun updateCollection(
        id: String,
        dto: UpdateCollectionDto
    ): DivaResult<ApiResponse<CollectionResponse>, DivaError> {
        // TODO: Implement when service has methods
        return DivaResult.failure(
            DivaError(
                cause = io.github.juevigrace.diva.core.errors.ErrorCause.Network.Error(
                    method = io.github.juevigrace.diva.core.network.HttpRequestMethod.PUT,
                    url = "/api/collection/$id",
                    status = io.github.juevigrace.diva.core.network.HttpStatusCodes.NotImplemented,
                    details = io.github.juevigrace.diva.core.Option.Some("Not yet implemented")
                )
            )
        )
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun deleteCollection(id: String): DivaResult<ApiResponse<Unit>, DivaError> {
        // TODO: Implement when service has methods
        return DivaResult.failure(
            DivaError(
                cause = io.github.juevigrace.diva.core.errors.ErrorCause.Network.Error(
                    method = io.github.juevigrace.diva.core.network.HttpRequestMethod.DELETE,
                    url = "/api/collection/$id",
                    status = io.github.juevigrace.diva.core.network.HttpStatusCodes.NotImplemented,
                    details = io.github.juevigrace.diva.core.Option.Some("Not yet implemented")
                )
            )
        )
    }
}
