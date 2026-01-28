package com.diva.user.data

import com.diva.models.api.ApiResponse
import com.diva.models.api.user.permissions.dtos.UserPermissionDto
import com.diva.models.api.user.permissions.response.UserPermissionResponse
import io.github.juevigrace.diva.core.DivaResult
import io.github.juevigrace.diva.core.errors.DivaError

interface UserPermissionsService {
    suspend fun getPermissions(
        userId: String
    ): DivaResult<ApiResponse<List<UserPermissionResponse>>, DivaError.NetworkError>

    suspend fun getPermission(
        userId: String,
        id: String
    ): DivaResult<ApiResponse<UserPermissionResponse>, DivaError.NetworkError>

    suspend fun createPermission(dto: UserPermissionDto): DivaResult<ApiResponse<Nothing>, DivaError.NetworkError>

    suspend fun updatePermission(dto: UserPermissionDto): DivaResult<ApiResponse<Nothing>, DivaError.NetworkError>

    suspend fun deletePermission(userId: String, id: String): DivaResult<ApiResponse<Nothing>, DivaError.NetworkError>
}
