package com.diva.collection.api.handler

import com.diva.models.api.ApiResponse
import com.diva.models.api.PaginationResponse
import com.diva.models.api.collection.playlist.dtos.CreatePlaylistDto
import com.diva.models.api.collection.playlist.dtos.UpdatePlaylistDto
import com.diva.models.api.collection.playlist.response.PlaylistResponse
import io.github.juevigrace.diva.core.DivaResult
import io.github.juevigrace.diva.core.errors.DivaError
import kotlin.uuid.ExperimentalUuidApi

interface PlaylistHandler {

    suspend fun getPlaylists(
        page: Int,
        pageSize: Int
    ): DivaResult<ApiResponse<PaginationResponse<PlaylistResponse>>, DivaError>

    @OptIn(ExperimentalUuidApi::class)
    suspend fun getPlaylist(id: String): DivaResult<ApiResponse<PlaylistResponse>, DivaError>

    suspend fun createPlaylist(dto: CreatePlaylistDto): DivaResult<ApiResponse<PlaylistResponse>, DivaError>

    @OptIn(ExperimentalUuidApi::class)
    suspend fun updatePlaylist(
        id: String,
        dto: UpdatePlaylistDto
    ): DivaResult<ApiResponse<PlaylistResponse>, DivaError>

    @OptIn(ExperimentalUuidApi::class)
    suspend fun deletePlaylist(id: String): DivaResult<ApiResponse<Unit>, DivaError>
}

class PlaylistHandlerImpl(
    private val service: com.diva.collection.data.playlist.PlaylistService
) : PlaylistHandler {
    override suspend fun getPlaylists(
        page: Int,
        pageSize: Int
    ): DivaResult<ApiResponse<PaginationResponse<PlaylistResponse>>, DivaError> {
        // TODO: Implement when service has methods
        return DivaResult.failure(
            DivaError(
                cause = io.github.juevigrace.diva.core.errors.ErrorCause.Network.Error(
                    method = io.github.juevigrace.diva.core.network.HttpRequestMethod.GET,
                    url = "/api/collection/playlist",
                    status = io.github.juevigrace.diva.core.network.HttpStatusCodes.NotImplemented,
                    details = io.github.juevigrace.diva.core.Option.Some("Not yet implemented")
                )
            )
        )
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun getPlaylist(id: String): DivaResult<ApiResponse<PlaylistResponse>, DivaError> {
        // TODO: Implement when service has methods
        return DivaResult.failure(
            DivaError(
                cause = io.github.juevigrace.diva.core.errors.ErrorCause.Network.Error(
                    method = io.github.juevigrace.diva.core.network.HttpRequestMethod.GET,
                    url = "/api/collection/playlist/$id",
                    status = io.github.juevigrace.diva.core.network.HttpStatusCodes.NotImplemented,
                    details = io.github.juevigrace.diva.core.Option.Some("Not yet implemented")
                )
            )
        )
    }

    override suspend fun createPlaylist(dto: CreatePlaylistDto): DivaResult<ApiResponse<PlaylistResponse>, DivaError> {
        // TODO: Implement when service has methods
        return DivaResult.failure(
            DivaError(
                cause = io.github.juevigrace.diva.core.errors.ErrorCause.Network.Error(
                    method = io.github.juevigrace.diva.core.network.HttpRequestMethod.POST,
                    url = "/api/collection/playlist",
                    status = io.github.juevigrace.diva.core.network.HttpStatusCodes.NotImplemented,
                    details = io.github.juevigrace.diva.core.Option.Some("Not yet implemented")
                )
            )
        )
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun updatePlaylist(
        id: String,
        dto: UpdatePlaylistDto
    ): DivaResult<ApiResponse<PlaylistResponse>, DivaError> {
        // TODO: Implement when service has methods
        return DivaResult.failure(
            DivaError(
                cause = io.github.juevigrace.diva.core.errors.ErrorCause.Network.Error(
                    method = io.github.juevigrace.diva.core.network.HttpRequestMethod.PUT,
                    url = "/api/collection/playlist/$id",
                    status = io.github.juevigrace.diva.core.network.HttpStatusCodes.NotImplemented,
                    details = io.github.juevigrace.diva.core.Option.Some("Not yet implemented")
                )
            )
        )
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun deletePlaylist(id: String): DivaResult<ApiResponse<Unit>, DivaError> {
        // TODO: Implement when service has methods
        return DivaResult.failure(
            DivaError(
                cause = io.github.juevigrace.diva.core.errors.ErrorCause.Network.Error(
                    method = io.github.juevigrace.diva.core.network.HttpRequestMethod.DELETE,
                    url = "/api/collection/playlist/$id",
                    status = io.github.juevigrace.diva.core.network.HttpStatusCodes.NotImplemented,
                    details = io.github.juevigrace.diva.core.Option.Some("Not yet implemented")
                )
            )
        )
    }
}
