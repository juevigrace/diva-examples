package com.diva.chat.api.handler

import com.diva.models.api.ApiResponse
import com.diva.models.api.PaginationResponse
import com.diva.models.api.chat.message.dtos.CreateChatMessageDto
import com.diva.models.api.chat.message.dtos.UpdateChatMessageDto
import com.diva.models.api.chat.message.response.ChatMessageResponse
import io.github.juevigrace.diva.core.DivaResult
import io.github.juevigrace.diva.core.errors.DivaError
import kotlin.uuid.ExperimentalUuidApi

interface ChatMessageHandler {

    suspend fun getChatMessages(
        page: Int,
        pageSize: Int
    ): DivaResult<ApiResponse<PaginationResponse<ChatMessageResponse>>, DivaError>

    @OptIn(ExperimentalUuidApi::class)
    suspend fun getChatMessage(id: String): DivaResult<ApiResponse<ChatMessageResponse>, DivaError>

    suspend fun createChatMessage(dto: CreateChatMessageDto): DivaResult<ApiResponse<ChatMessageResponse>, DivaError>

    @OptIn(ExperimentalUuidApi::class)
    suspend fun updateChatMessage(
        id: String,
        dto: UpdateChatMessageDto
    ): DivaResult<ApiResponse<ChatMessageResponse>, DivaError>

    @OptIn(ExperimentalUuidApi::class)
    suspend fun deleteChatMessage(id: String): DivaResult<ApiResponse<Unit>, DivaError>
}

class ChatMessageHandlerImpl(
    private val service: com.diva.chat.data.ChatMessageService
) : ChatMessageHandler {
    override suspend fun getChatMessages(
        page: Int,
        pageSize: Int
    ): DivaResult<ApiResponse<PaginationResponse<ChatMessageResponse>>, DivaError> {
        // TODO: Implement when service has methods
        return DivaResult.failure(
            DivaError(
                cause = io.github.juevigrace.diva.core.errors.ErrorCause.Network.Error(
                    method = io.github.juevigrace.diva.core.network.HttpRequestMethod.GET,
                    url = "/api/chat/message",
                    status = io.github.juevigrace.diva.core.network.HttpStatusCodes.NotImplemented,
                    details = io.github.juevigrace.diva.core.Option.Some("Not yet implemented")
                )
            )
        )
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun getChatMessage(id: String): DivaResult<ApiResponse<ChatMessageResponse>, DivaError> {
        // TODO: Implement when service has methods
        return DivaResult.failure(
            DivaError(
                cause = io.github.juevigrace.diva.core.errors.ErrorCause.Network.Error(
                    method = io.github.juevigrace.diva.core.network.HttpRequestMethod.GET,
                    url = "/api/chat/message/$id",
                    status = io.github.juevigrace.diva.core.network.HttpStatusCodes.NotImplemented,
                    details = io.github.juevigrace.diva.core.Option.Some("Not yet implemented")
                )
            )
        )
    }

    override suspend fun createChatMessage(dto: CreateChatMessageDto): DivaResult<ApiResponse<ChatMessageResponse>, DivaError> {
        // TODO: Implement when service has methods
        return DivaResult.failure(
            DivaError(
                cause = io.github.juevigrace.diva.core.errors.ErrorCause.Network.Error(
                    method = io.github.juevigrace.diva.core.network.HttpRequestMethod.POST,
                    url = "/api/chat/message",
                    status = io.github.juevigrace.diva.core.network.HttpStatusCodes.NotImplemented,
                    details = io.github.juevigrace.diva.core.Option.Some("Not yet implemented")
                )
            )
        )
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun updateChatMessage(
        id: String,
        dto: UpdateChatMessageDto
    ): DivaResult<ApiResponse<ChatMessageResponse>, DivaError> {
        // TODO: Implement when service has methods
        return DivaResult.failure(
            DivaError(
                cause = io.github.juevigrace.diva.core.errors.ErrorCause.Network.Error(
                    method = io.github.juevigrace.diva.core.network.HttpRequestMethod.PUT,
                    url = "/api/chat/message/$id",
                    status = io.github.juevigrace.diva.core.network.HttpStatusCodes.NotImplemented,
                    details = io.github.juevigrace.diva.core.Option.Some("Not yet implemented")
                )
            )
        )
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun deleteChatMessage(id: String): DivaResult<ApiResponse<Unit>, DivaError> {
        // TODO: Implement when service has methods
        return DivaResult.failure(
            DivaError(
                cause = io.github.juevigrace.diva.core.errors.ErrorCause.Network.Error(
                    method = io.github.juevigrace.diva.core.network.HttpRequestMethod.DELETE,
                    url = "/api/chat/message/$id",
                    status = io.github.juevigrace.diva.core.network.HttpStatusCodes.NotImplemented,
                    details = io.github.juevigrace.diva.core.Option.Some("Not yet implemented")
                )
            )
        )
    }
}
