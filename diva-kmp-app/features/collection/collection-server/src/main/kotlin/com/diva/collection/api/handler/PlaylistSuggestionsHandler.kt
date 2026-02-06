package com.diva.collection.api.handler

import com.diva.models.api.ApiResponse
import com.diva.models.api.PaginationResponse
import com.diva.models.api.collection.playlist.suggestions.dtos.CreatePlaylistSuggestionDto
import com.diva.models.api.collection.playlist.suggestions.dtos.UpdatePlaylistSuggestionDto
import com.diva.models.api.collection.playlist.suggestions.response.PlaylistSuggestionResponse
import io.github.juevigrace.diva.core.DivaResult
import io.github.juevigrace.diva.core.errors.DivaError
import kotlin.uuid.ExperimentalUuidApi

interface PlaylistSuggestionsHandler {

    suspend fun getPlaylistSuggestions(
        page: Int,
        pageSize: Int
    ): DivaResult<ApiResponse<PaginationResponse<PlaylistSuggestionResponse>>, DivaError>

    @OptIn(ExperimentalUuidApi::class)
    suspend fun getPlaylistSuggestion(id: String): DivaResult<ApiResponse<PlaylistSuggestionResponse>, DivaError>

    suspend fun createPlaylistSuggestion(dto: CreatePlaylistSuggestionDto): DivaResult<ApiResponse<PlaylistSuggestionResponse>, DivaError>

    @OptIn(ExperimentalUuidApi::class)
    suspend fun updatePlaylistSuggestion(
        id: String,
        dto: UpdatePlaylistSuggestionDto
    ): DivaResult<ApiResponse<PlaylistSuggestionResponse>, DivaError>

    @OptIn(ExperimentalUuidApi::class)
    suspend fun deletePlaylistSuggestion(id: String): DivaResult<ApiResponse<Unit>, DivaError>
}

class PlaylistSuggestionsHandlerImpl(
    private val service: com.diva.collection.data.playlist.PlaylistSuggestionService
) : PlaylistSuggestionsHandler {
    override suspend fun getPlaylistSuggestions(
        page: Int,
        pageSize: Int
    ): DivaResult<ApiResponse<PaginationResponse<PlaylistSuggestionResponse>>, DivaError> {
        // TODO: Implement when service has methods
        return DivaResult.failure(
            DivaError(
                cause = io.github.juevigrace.diva.core.errors.ErrorCause.Network.Error(
                    method = io.github.juevigrace.diva.core.network.HttpRequestMethod.GET,
                    url = "/api/collection/playlist/suggestions",
                    status = io.github.juevigrace.diva.core.network.HttpStatusCodes.NotImplemented,
                    details = io.github.juevigrace.diva.core.Option.Some("Not yet implemented")
                )
            )
        )
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun getPlaylistSuggestion(id: String): DivaResult<ApiResponse<PlaylistSuggestionResponse>, DivaError> {
        // TODO: Implement when service has methods
        return DivaResult.failure(
            DivaError(
                cause = io.github.juevigrace.diva.core.errors.ErrorCause.Network.Error(
                    method = io.github.juevigrace.diva.core.network.HttpRequestMethod.GET,
                    url = "/api/collection/playlist/suggestions/$id",
                    status = io.github.juevigrace.diva.core.network.HttpStatusCodes.NotImplemented,
                    details = io.github.juevigrace.diva.core.Option.Some("Not yet implemented")
                )
            )
        )
    }

    override suspend fun createPlaylistSuggestion(dto: CreatePlaylistSuggestionDto): DivaResult<ApiResponse<PlaylistSuggestionResponse>, DivaError> {
        // TODO: Implement when service has methods
        return DivaResult.failure(
            DivaError(
                cause = io.github.juevigrace.diva.core.errors.ErrorCause.Network.Error(
                    method = io.github.juevigrace.diva.core.network.HttpRequestMethod.POST,
                    url = "/api/collection/playlist/suggestions",
                    status = io.github.juevigrace.diva.core.network.HttpStatusCodes.NotImplemented,
                    details = io.github.juevigrace.diva.core.Option.Some("Not yet implemented")
                )
            )
        )
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun updatePlaylistSuggestion(
        id: String,
        dto: UpdatePlaylistSuggestionDto
    ): DivaResult<ApiResponse<PlaylistSuggestionResponse>, DivaError> {
        // TODO: Implement when service has methods
        return DivaResult.failure(
            DivaError(
                cause = io.github.juevigrace.diva.core.errors.ErrorCause.Network.Error(
                    method = io.github.juevigrace.diva.core.network.HttpRequestMethod.PUT,
                    url = "/api/collection/playlist/suggestions/$id",
                    status = io.github.juevigrace.diva.core.network.HttpStatusCodes.NotImplemented,
                    details = io.github.juevigrace.diva.core.Option.Some("Not yet implemented")
                )
            )
        )
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun deletePlaylistSuggestion(id: String): DivaResult<ApiResponse<Unit>, DivaError> {
        // TODO: Implement when service has methods
        return DivaResult.failure(
            DivaError(
                cause = io.github.juevigrace.diva.core.errors.ErrorCause.Network.Error(
                    method = io.github.juevigrace.diva.core.network.HttpRequestMethod.DELETE,
                    url = "/api/collection/playlist/suggestions/$id",
                    status = io.github.juevigrace.diva.core.network.HttpStatusCodes.NotImplemented,
                    details = io.github.juevigrace.diva.core.Option.Some("Not yet implemented")
                )
            )
        )
    }
}
