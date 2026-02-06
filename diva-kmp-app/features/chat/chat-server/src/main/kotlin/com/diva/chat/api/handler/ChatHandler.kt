package com.diva.chat.api.handler

import com.diva.models.api.ApiResponse
import com.diva.models.api.PaginationResponse
import com.diva.models.api.chat.dtos.CreateChatDto
import com.diva.models.api.chat.dtos.UpdateChatDto
import com.diva.models.api.chat.response.ChatResponse
import io.github.juevigrace.diva.core.DivaResult
import io.github.juevigrace.diva.core.errors.DivaError
import kotlin.uuid.ExperimentalUuidApi

interface ChatHandler {

    suspend fun getChats(
        page: Int,
        pageSize: Int
    ): DivaResult<ApiResponse<PaginationResponse<ChatResponse>>, DivaError>

    @OptIn(ExperimentalUuidApi::class)
    suspend fun getChat(id: String): DivaResult<ApiResponse<ChatResponse>, DivaError>

    suspend fun createChat(dto: CreateChatDto): DivaResult<ApiResponse<ChatResponse>, DivaError>

    @OptIn(ExperimentalUuidApi::class)
    suspend fun updateChat(
        id: String,
        dto: UpdateChatDto
    ): DivaResult<ApiResponse<ChatResponse>, DivaError>

    @OptIn(ExperimentalUuidApi::class)
    suspend fun deleteChat(id: String): DivaResult<ApiResponse<Unit>, DivaError>
}

class ChatHandlerImpl(
    private val service: com.diva.chat.data.ChatService
) : ChatHandler {
    override suspend fun getChats(
        page: Int,
        pageSize: Int
    ): DivaResult<ApiResponse<PaginationResponse<ChatResponse>>, DivaError> {
        // TODO: Implement when service has methods
        return DivaResult.failure(
            DivaError(
                cause = io.github.juevigrace.diva.core.errors.ErrorCause.Network.Error(
                    method = io.github.juevigrace.diva.core.network.HttpRequestMethod.GET,
                    url = "/api/chat",
                    status = io.github.juevigrace.diva.core.network.HttpStatusCodes.NotImplemented,
                    details = io.github.juevigrace.diva.core.Option.Some("Not yet implemented")
                )
            )
        )
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun getChat(id: String): DivaResult<ApiResponse<ChatResponse>, DivaError> {
        // TODO: Implement when service has methods
        return DivaResult.failure(
            DivaError(
                cause = io.github.juevigrace.diva.core.errors.ErrorCause.Network.Error(
                    method = io.github.juevigrace.diva.core.network.HttpRequestMethod.GET,
                    url = "/api/chat/$id",
                    status = io.github.juevigrace.diva.core.network.HttpStatusCodes.NotImplemented,
                    details = io.github.juevigrace.diva.core.Option.Some("Not yet implemented")
                )
            )
        )
    }

    override suspend fun createChat(dto: CreateChatDto): DivaResult<ApiResponse<ChatResponse>, DivaError> {
        // TODO: Implement when service has methods
        return DivaResult.failure(
            DivaError(
                cause = io.github.juevigrace.diva.core.errors.ErrorCause.Network.Error(
                    method = io.github.juevigrace.diva.core.network.HttpRequestMethod.POST,
                    url = "/api/chat",
                    status = io.github.juevigrace.diva.core.network.HttpStatusCodes.NotImplemented,
                    details = io.github.juevigrace.diva.core.Option.Some("Not yet implemented")
                )
            )
        )
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun updateChat(
        id: String,
        dto: UpdateChatDto
    ): DivaResult<ApiResponse<ChatResponse>, DivaError> {
        // TODO: Implement when service has methods
        return DivaResult.failure(
            DivaError(
                cause = io.github.juevigrace.diva.core.errors.ErrorCause.Network.Error(
                    method = io.github.juevigrace.diva.core.network.HttpRequestMethod.PUT,
                    url = "/api/chat/$id",
                    status = io.github.juevigrace.diva.core.network.HttpStatusCodes.NotImplemented,
                    details = io.github.juevigrace.diva.core.Option.Some("Not yet implemented")
                )
            )
        )
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun deleteChat(id: String): DivaResult<ApiResponse<Unit>, DivaError> {
        // TODO: Implement when service has methods
        return DivaResult.failure(
            DivaError(
                cause = io.github.juevigrace.diva.core.errors.ErrorCause.Network.Error(
                    method = io.github.juevigrace.diva.core.network.HttpRequestMethod.DELETE,
                    url = "/api/chat/$id",
                    status = io.github.juevigrace.diva.core.network.HttpStatusCodes.NotImplemented,
                    details = io.github.juevigrace.diva.core.Option.Some("Not yet implemented")
                )
            )
        )
    }
}
