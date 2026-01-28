package com.diva.user.data

import com.diva.models.api.ApiResponse
import com.diva.models.api.PaginationResponse
import com.diva.models.api.user.dtos.CreateUserDto
import com.diva.models.api.user.dtos.UpdateUserDto
import com.diva.models.api.user.dtos.UserEmailDto
import com.diva.models.auth.Session
import com.diva.models.user.User
import io.github.juevigrace.diva.core.DivaResult
import io.github.juevigrace.diva.core.errors.DivaError
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

interface UserService {
    suspend fun getUsers(
        page: Int,
        pageSize: Int
    ): DivaResult<ApiResponse<PaginationResponse<User>>, DivaError.NetworkError>

    suspend fun getUser(id: String): DivaResult<ApiResponse<User>, DivaError.NetworkError>

    suspend fun updateUser(
        dto: UpdateUserDto,
        id: String,
    ): DivaResult<ApiResponse<Nothing>, DivaError.NetworkError>

    suspend fun updateEmail(
        dto: UserEmailDto,
        session: Session,
    ): DivaResult<ApiResponse<Nothing>, DivaError.NetworkError>

    suspend fun deleteUser(id: String): DivaResult<ApiResponse<Nothing>, DivaError.NetworkError>

    suspend fun getUserByUsername(username: String): DivaResult<User, DivaError>

    suspend fun getUserByEmail(email: String): DivaResult<User, DivaError>

    @OptIn(ExperimentalUuidApi::class)
    suspend fun createUser(
        dto: CreateUserDto,
        onVerification: suspend (userId: Uuid) -> DivaResult<Unit, DivaError>
    ): DivaResult<Uuid, DivaError>

    @OptIn(ExperimentalUuidApi::class)
    suspend fun updatePassword(id: Uuid, passwordHash: String): DivaResult<Unit, DivaError>

    @OptIn(ExperimentalUuidApi::class)
    suspend fun updateVerified(id: Uuid): DivaResult<Unit, DivaError>
}
