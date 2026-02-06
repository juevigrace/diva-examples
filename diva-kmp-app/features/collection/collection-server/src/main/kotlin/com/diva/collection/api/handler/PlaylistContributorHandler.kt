package com.diva.collection.api.handler

import com.diva.models.api.ApiResponse
import com.diva.models.api.PaginationResponse
import com.diva.models.api.collection.playlist.contributor.dtos.CreatePlaylistContributorDto
import com.diva.models.api.collection.playlist.contributor.dtos.UpdatePlaylistContributorDto
import com.diva.models.api.collection.playlist.contributor.response.PlaylistContributorResponse
import io.github.juevigrace.diva.core.DivaResult
import io.github.juevigrace.diva.core.errors.DivaError
import kotlin.uuid.ExperimentalUuidApi

interface PlaylistContributorHandler {

    suspend fun getPlaylistContributors(
        page: Int,
        pageSize: Int
    ): DivaResult<ApiResponse<PaginationResponse<PlaylistContributorResponse>>, DivaError>

    @OptIn(ExperimentalUuidApi::class)
    suspend fun getPlaylistContributor(id: String): DivaResult<ApiResponse<PlaylistContributorResponse>, DivaError>

    suspend fun createPlaylistContributor(dto: CreatePlaylistContributorDto): DivaResult<ApiResponse<PlaylistContributorResponse>, DivaError>

    @OptIn(ExperimentalUuidApi::class)
    suspend fun updatePlaylistContributor(
        id: String,
        dto: UpdatePlaylistContributorDto
    ): DivaResult<ApiResponse<PlaylistContributorResponse>, DivaError>

    @OptIn(ExperimentalUuidApi::class)
    suspend fun deletePlaylistContributor(id: String): DivaResult<ApiResponse<Unit>, DivaError>
}

class PlaylistContributorHandlerImpl(
    private val service: com.diva.collection.data.playlist.PlaylistContributorService
) : PlaylistContributorHandler {
    override suspend fun getPlaylistContributors(
        page: Int,
        pageSize: Int
    ): DivaResult<ApiResponse<PaginationResponse<PlaylistContributorResponse>>, DivaError> {
        // TODO: Implement when service has methods
        return DivaResult.failure(
            DivaError(
                cause = io.github.juevigrace.diva.core.errors.ErrorCause.Network.Error(
                    method = io.github.juevigrace.diva.core.network.HttpRequestMethod.GET,
                    url = "/api/collection/playlist/contributor",
                    status = io.github.juevigrace.diva.core.network.HttpStatusCodes.NotImplemented,
                    details = io.github.juevigrace.diva.core.Option.Some("Not yet implemented")
                )
            )
        )
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun getPlaylistContributor(id: String): DivaResult<ApiResponse<PlaylistContributorResponse>, DivaError> {
        // TODO: Implement when service has methods
        return DivaResult.failure(
            DivaError(
                cause = io.github.juevigrace.diva.core.errors.ErrorCause.Network.Error(
                    method = io.github.juevigrace.diva.core.network.HttpRequestMethod.GET,
                    url = "/api/collection/playlist/contributor/$id",
                    status = io.github.juevigrace.diva.core.network.HttpStatusCodes.NotImplemented,
                    details = io.github.juevigrace.diva.core.Option.Some("Not yet implemented")
                )
            )
        )
    }

    override suspend fun createPlaylistContributor(dto: CreatePlaylistContributorDto): DivaResult<ApiResponse<PlaylistContributorResponse>, DivaError> {
        // TODO: Implement when service has methods
        return DivaResult.failure(
            DivaError(
                cause = io.github.juevigrace.diva.core.errors.ErrorCause.Network.Error(
                    method = io.github.juevigrace.diva.core.network.HttpRequestMethod.POST,
                    url = "/api/collection/playlist/contributor",
                    status = io.github.juevigrace.diva.core.network.HttpStatusCodes.NotImplemented,
                    details = io.github.juevigrace.diva.core.Option.Some("Not yet implemented")
                )
            )
        )
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun updatePlaylistContributor(
        id: String,
        dto: UpdatePlaylistContributorDto
    ): DivaResult<ApiResponse<PlaylistContributorResponse>, DivaError> {
        // TODO: Implement when service has methods
        return DivaResult.failure(
            DivaError(
                cause = io.github.juevigrace.diva.core.errors.ErrorCause.Network.Error(
                    method = io.github.juevigrace.diva.core.network.HttpRequestMethod.PUT,
                    url = "/api/collection/playlist/contributor/$id",
                    status = io.github.juevigrace.diva.core.network.HttpStatusCodes.NotImplemented,
                    details = io.github.juevigrace.diva.core.Option.Some("Not yet implemented")
                )
            )
        )
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun deletePlaylistContributor(id: String): DivaResult<ApiResponse<Unit>, DivaError> {
        // TODO: Implement when service has methods
        return DivaResult.failure(
            DivaError(
                cause = io.github.juevigrace.diva.core.errors.ErrorCause.Network.Error(
                    method = io.github.juevigrace.diva.core.network.HttpRequestMethod.DELETE,
                    url = "/api/collection/playlist/contributor/$id",
                    status = io.github.juevigrace.diva.core.network.HttpStatusCodes.NotImplemented,
                    details = io.github.juevigrace.diva.core.Option.Some("Not yet implemented")
                )
            )
        )
    }
}
