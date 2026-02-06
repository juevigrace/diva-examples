package com.diva.permissions.api.handler

import com.diva.models.api.ApiResponse
import com.diva.models.api.PaginationResponse
import com.diva.models.api.permissions.dtos.CreatePermissionDto
import com.diva.models.api.permissions.dtos.UpdatePermissionDto
import com.diva.models.api.permissions.response.PermissionResponse
import com.diva.models.permission.Permission
import io.github.juevigrace.diva.core.DivaResult
import io.github.juevigrace.diva.core.errors.DivaError
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

interface PermissionsHandler {

    suspend fun getPermissions(
        page: Int,
        pageSize: Int
    ): DivaResult<ApiResponse<PaginationResponse<PermissionResponse>>, DivaError>

    @OptIn(ExperimentalUuidApi::class)
    suspend fun getPermission(id: String): DivaResult<ApiResponse<PermissionResponse>, DivaError>

    suspend fun createPermission(dto: CreatePermissionDto): DivaResult<ApiResponse<PermissionResponse>, DivaError>

    @OptIn(ExperimentalUuidApi::class)
    suspend fun updatePermission(
        id: String,
        dto: UpdatePermissionDto
    ): DivaResult<ApiResponse<PermissionResponse>, DivaError>

    @OptIn(ExperimentalUuidApi::class)
    suspend fun deletePermission(id: String): DivaResult<ApiResponse<Unit>, DivaError>
}

class PermissionsHandlerImpl(
    private val service: com.diva.permissions.data.PermissionsService
) : PermissionsHandler {
    override suspend fun getPermissions(
        page: Int,
        pageSize: Int
    ): DivaResult<ApiResponse<PaginationResponse<PermissionResponse>>, DivaError> {
        // TODO: Implement when service has methods
        return DivaResult.failure(
            DivaError(
                cause = io.github.juevigrace.diva.core.errors.ErrorCause.Network.Error(
                    method = io.github.juevigrace.diva.core.network.HttpRequestMethod.GET,
                    url = "/api/permissions",
                    status = io.github.juevigrace.diva.core.network.HttpStatusCodes.NotImplemented,
                    details = io.github.juevigrace.diva.core.Option.Some("Not yet implemented")
                )
            )
        )
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun getPermission(id: String): DivaResult<ApiResponse<PermissionResponse>, DivaError> {
        // TODO: Implement when service has methods
        return DivaResult.failure(
            DivaError(
                cause = io.github.juevigrace.diva.core.errors.ErrorCause.Network.Error(
                    method = io.github.juevigrace.diva.core.network.HttpRequestMethod.GET,
                    url = "/api/permissions/$id",
                    status = io.github.juevigrace.diva.core.network.HttpStatusCodes.NotImplemented,
                    details = io.github.juevigrace.diva.core.Option.Some("Not yet implemented")
                )
            )
        )
    }

    override suspend fun createPermission(dto: CreatePermissionDto): DivaResult<ApiResponse<PermissionResponse>, DivaError> {
        // TODO: Implement when service has methods
        return DivaResult.failure(
            DivaError(
                cause = io.github.juevigrace.diva.core.errors.ErrorCause.Network.Error(
                    method = io.github.juevigrace.diva.core.network.HttpRequestMethod.POST,
                    url = "/api/permissions",
                    status = io.github.juevigrace.diva.core.network.HttpStatusCodes.NotImplemented,
                    details = io.github.juevigrace.diva.core.Option.Some("Not yet implemented")
                )
            )
        )
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun updatePermission(
        id: String,
        dto: UpdatePermissionDto
    ): DivaResult<ApiResponse<PermissionResponse>, DivaError> {
        // TODO: Implement when service has methods
        return DivaResult.failure(
            DivaError(
                cause = io.github.juevigrace.diva.core.errors.ErrorCause.Network.Error(
                    method = io.github.juevigrace.diva.core.network.HttpRequestMethod.PUT,
                    url = "/api/permissions/$id",
                    status = io.github.juevigrace.diva.core.network.HttpStatusCodes.NotImplemented,
                    details = io.github.juevigrace.diva.core.Option.Some("Not yet implemented")
                )
            )
        )
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun deletePermission(id: String): DivaResult<ApiResponse<Unit>, DivaError> {
        // TODO: Implement when service has methods
        return DivaResult.failure(
            DivaError(
                cause = io.github.juevigrace.diva.core.errors.ErrorCause.Network.Error(
                    method = io.github.juevigrace.diva.core.network.HttpRequestMethod.DELETE,
                    url = "/api/permissions/$id",
                    status = io.github.juevigrace.diva.core.network.HttpStatusCodes.NotImplemented,
                    details = io.github.juevigrace.diva.core.Option.Some("Not yet implemented")
                )
            )
        )
    }
}