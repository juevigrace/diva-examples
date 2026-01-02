package com.diva.user.data

import com.diva.models.api.ApiResponse
import com.diva.models.api.user.dtos.CreateUserDto
import com.diva.models.api.user.dtos.UpdateUserDto
import com.diva.models.user.User
import io.github.juevigrace.diva.core.models.DivaError
import io.github.juevigrace.diva.core.models.DivaResult
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

interface UserService {
    suspend fun getUsers(limit: Int, offset: Int): DivaResult<ApiResponse<List<User>>, DivaError>

    @OptIn(ExperimentalUuidApi::class)
    suspend fun getUser(id: Uuid): DivaResult<ApiResponse<User>, DivaError>

    suspend fun getUserByUsername(username: String): DivaResult<ApiResponse<User>, DivaError>

    suspend fun getUserByEmail(email: String): DivaResult<ApiResponse<User>, DivaError>

    @OptIn(ExperimentalUuidApi::class)
    suspend fun createUser(user: CreateUserDto): DivaResult<Uuid, DivaError>

    suspend fun updateUser(user: UpdateUserDto): DivaResult<ApiResponse<User>, DivaError>

    @OptIn(ExperimentalUuidApi::class)
    suspend fun updatePassword(id: Uuid, passwordHash: String): DivaResult<Unit, DivaError>

    @OptIn(ExperimentalUuidApi::class)
    suspend fun deleteUser(id: Uuid): DivaResult<Unit, DivaError>
}
