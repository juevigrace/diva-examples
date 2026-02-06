package com.diva.chat.api.handler

import com.diva.models.api.ApiResponse
import com.diva.models.api.PaginationResponse
import com.diva.models.api.chat.participant.dtos.CreateChatParticipantDto
import com.diva.models.api.chat.participant.dtos.UpdateChatParticipantDto
import com.diva.models.api.chat.participant.response.ChatParticipantResponse
import io.github.juevigrace.diva.core.DivaResult
import io.github.juevigrace.diva.core.errors.DivaError
import kotlin.uuid.ExperimentalUuidApi

interface ChatParticipantHandler {

    suspend fun getChatParticipants(
        page: Int,
        pageSize: Int
    ): DivaResult<ApiResponse<PaginationResponse<ChatParticipantResponse>>, DivaError>

    @OptIn(ExperimentalUuidApi::class)
    suspend fun getChatParticipant(id: String): DivaResult<ApiResponse<ChatParticipantResponse>, DivaError>

    suspend fun createChatParticipant(dto: CreateChatParticipantDto): DivaResult<ApiResponse<ChatParticipantResponse>, DivaError>

    @OptIn(ExperimentalUuidApi::class)
    suspend fun updateChatParticipant(
        id: String,
        dto: UpdateChatParticipantDto
    ): DivaResult<ApiResponse<ChatParticipantResponse>, DivaError>

    @OptIn(ExperimentalUuidApi::class)
    suspend fun deleteChatParticipant(id: String): DivaResult<ApiResponse<Unit>, DivaError>
}

class ChatParticipantHandlerImpl(
    private val service: com.diva.chat.data.ChatParticipantService
) : ChatParticipantHandler {
    override suspend fun getChatParticipants(
        page: Int,
        pageSize: Int
    ): DivaResult<ApiResponse<PaginationResponse<ChatParticipantResponse>>, DivaError> {
        // TODO: Implement when service has methods
        return DivaResult.failure(
            DivaError(
                cause = io.github.juevigrace.diva.core.errors.ErrorCause.Network.Error(
                    method = io.github.juevigrace.diva.core.network.HttpRequestMethod.GET,
                    url = "/api/chat/participant",
                    status = io.github.juevigrace.diva.core.network.HttpStatusCodes.NotImplemented,
                    details = io.github.juevigrace.diva.core.Option.Some("Not yet implemented")
                )
            )
        )
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun getChatParticipant(id: String): DivaResult<ApiResponse<ChatParticipantResponse>, DivaError> {
        // TODO: Implement when service has methods
        return DivaResult.failure(
            DivaError(
                cause = io.github.juevigrace.diva.core.errors.ErrorCause.Network.Error(
                    method = io.github.juevigrace.diva.core.network.HttpRequestMethod.GET,
                    url = "/api/chat/participant/$id",
                    status = io.github.juevigrace.diva.core.network.HttpStatusCodes.NotImplemented,
                    details = io.github.juevigrace.diva.core.Option.Some("Not yet implemented")
                )
            )
        )
    }

    override suspend fun createChatParticipant(dto: CreateChatParticipantDto): DivaResult<ApiResponse<ChatParticipantResponse>, DivaError> {
        // TODO: Implement when service has methods
        return DivaResult.failure(
            DivaError(
                cause = io.github.juevigrace.diva.core.errors.ErrorCause.Network.Error(
                    method = io.github.juevigrace.diva.core.network.HttpRequestMethod.POST,
                    url = "/api/chat/participant",
                    status = io.github.juevigrace.diva.core.network.HttpStatusCodes.NotImplemented,
                    details = io.github.juevigrace.diva.core.Option.Some("Not yet implemented")
                )
            )
        )
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun updateChatParticipant(
        id: String,
        dto: UpdateChatParticipantDto
    ): DivaResult<ApiResponse<ChatParticipantResponse>, DivaError> {
        // TODO: Implement when service has methods
        return DivaResult.failure(
            DivaError(
                cause = io.github.juevigrace.diva.core.errors.ErrorCause.Network.Error(
                    method = io.github.juevigrace.diva.core.network.HttpRequestMethod.PUT,
                    url = "/api/chat/participant/$id",
                    status = io.github.juevigrace.diva.core.network.HttpStatusCodes.NotImplemented,
                    details = io.github.juevigrace.diva.core.Option.Some("Not yet implemented")
                )
            )
        )
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun deleteChatParticipant(id: String): DivaResult<ApiResponse<Unit>, DivaError> {
        // TODO: Implement when service has methods
        return DivaResult.failure(
            DivaError(
                cause = io.github.juevigrace.diva.core.errors.ErrorCause.Network.Error(
                    method = io.github.juevigrace.diva.core.network.HttpRequestMethod.DELETE,
                    url = "/api/chat/participant/$id",
                    status = io.github.juevigrace.diva.core.network.HttpStatusCodes.NotImplemented,
                    details = io.github.juevigrace.diva.core.Option.Some("Not yet implemented")
                )
            )
        )
    }
}
