package com.diva.social.api.handler

import com.diva.models.api.ApiResponse
import com.diva.models.api.PaginationResponse
import com.diva.models.api.social.interaction.dtos.CreateInteractionDto
import com.diva.models.api.social.interaction.dtos.UpdateInteractionDto
import com.diva.models.api.social.interaction.response.InteractionResponse
import io.github.juevigrace.diva.core.DivaResult
import io.github.juevigrace.diva.core.errors.DivaError
import kotlin.uuid.ExperimentalUuidApi

interface InteractionHandler {

    suspend fun getInteractions(
        page: Int,
        pageSize: Int
    ): DivaResult<ApiResponse<PaginationResponse<InteractionResponse>>, DivaError>

    @OptIn(ExperimentalUuidApi::class)
    suspend fun getInteraction(id: String): DivaResult<ApiResponse<InteractionResponse>, DivaError>

    suspend fun createInteraction(dto: CreateInteractionDto): DivaResult<ApiResponse<InteractionResponse>, DivaError>

    @OptIn(ExperimentalUuidApi::class)
    suspend fun updateInteraction(
        id: String,
        dto: UpdateInteractionDto
    ): DivaResult<ApiResponse<InteractionResponse>, DivaError>

    @OptIn(ExperimentalUuidApi::class)
    suspend fun deleteInteraction(id: String): DivaResult<ApiResponse<Unit>, DivaError>
}

class InteractionHandlerImpl(
    private val service: com.diva.social.data.InteractionService
) : InteractionHandler {
    override suspend fun getInteractions(
        page: Int,
        pageSize: Int
    ): DivaResult<ApiResponse<PaginationResponse<InteractionResponse>>, DivaError> {
        // TODO: Implement when service has methods
        return DivaResult.failure(
            DivaError(
                cause = io.github.juevigrace.diva.core.errors.ErrorCause.Network.Error(
                    method = io.github.juevigrace.diva.core.network.HttpRequestMethod.GET,
                    url = "/api/interaction",
                    status = io.github.juevigrace.diva.core.network.HttpStatusCodes.NotImplemented,
                    details = io.github.juevigrace.diva.core.Option.Some("Not yet implemented")
                )
            )
        )
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun getInteraction(id: String): DivaResult<ApiResponse<InteractionResponse>, DivaError> {
        // TODO: Implement when service has methods
        return DivaResult.failure(
            DivaError(
                cause = io.github.juevigrace.diva.core.errors.ErrorCause.Network.Error(
                    method = io.github.juevigrace.diva.core.network.HttpRequestMethod.GET,
                    url = "/api/interaction/$id",
                    status = io.github.juevigrace.diva.core.network.HttpStatusCodes.NotImplemented,
                    details = io.github.juevigrace.diva.core.Option.Some("Not yet implemented")
                )
            )
        )
    }

    override suspend fun createInteraction(dto: CreateInteractionDto): DivaResult<ApiResponse<InteractionResponse>, DivaError> {
        // TODO: Implement when service has methods
        return DivaResult.failure(
            DivaError(
                cause = io.github.juevigrace.diva.core.errors.ErrorCause.Network.Error(
                    method = io.github.juevigrace.diva.core.network.HttpRequestMethod.POST,
                    url = "/api/interaction",
                    status = io.github.juevigrace.diva.core.network.HttpStatusCodes.NotImplemented,
                    details = io.github.juevigrace.diva.core.Option.Some("Not yet implemented")
                )
            )
        )
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun updateInteraction(
        id: String,
        dto: UpdateInteractionDto
    ): DivaResult<ApiResponse<InteractionResponse>, DivaError> {
        // TODO: Implement when service has methods
        return DivaResult.failure(
            DivaError(
                cause = io.github.juevigrace.diva.core.errors.ErrorCause.Network.Error(
                    method = io.github.juevigrace.diva.core.network.HttpRequestMethod.PUT,
                    url = "/api/interaction/$id",
                    status = io.github.juevigrace.diva.core.network.HttpStatusCodes.NotImplemented,
                    details = io.github.juevigrace.diva.core.Option.Some("Not yet implemented")
                )
            )
        )
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun deleteInteraction(id: String): DivaResult<ApiResponse<Unit>, DivaError> {
        // TODO: Implement when service has methods
        return DivaResult.failure(
            DivaError(
                cause = io.github.juevigrace.diva.core.errors.ErrorCause.Network.Error(
                    method = io.github.juevigrace.diva.core.network.HttpRequestMethod.DELETE,
                    url = "/api/interaction/$id",
                    status = io.github.juevigrace.diva.core.network.HttpStatusCodes.NotImplemented,
                    details = io.github.juevigrace.diva.core.Option.Some("Not yet implemented")
                )
            )
        )
    }
}