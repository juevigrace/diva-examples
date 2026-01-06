package com.diva.user.data

import com.diva.models.api.ApiResponse
import com.diva.models.api.user.dtos.CreateUserDto
import com.diva.models.api.user.dtos.UpdateUserDto
import com.diva.models.api.user.dtos.UpdateUserEmailDto
import com.diva.models.auth.Session
import com.diva.models.user.User
import io.github.juevigrace.diva.core.models.DivaError
import io.github.juevigrace.diva.core.models.DivaResult
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

interface UserService {
    suspend fun getUsers(limit: Int, offset: Int): DivaResult<ApiResponse<List<User>>, DivaError>

    suspend fun getUser(id: String): DivaResult<ApiResponse<User>, DivaError>

    suspend fun updateUser(dto: UpdateUserDto, session: Session): DivaResult<ApiResponse<Nothing>, DivaError>

    suspend fun updateEmail(
        dto: UpdateUserEmailDto,
    ): DivaResult<ApiResponse<Nothing>, DivaError>

    @OptIn(ExperimentalUuidApi::class)
    suspend fun deleteUser(id: Uuid): DivaResult<ApiResponse<Nothing>, DivaError>

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
