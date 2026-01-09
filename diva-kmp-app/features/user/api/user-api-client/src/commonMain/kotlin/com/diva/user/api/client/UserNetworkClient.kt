package com.diva.user.api.client

import com.diva.models.api.PaginationResponse
import com.diva.models.api.user.dtos.CreateUserDto
import com.diva.models.api.user.dtos.UpdateUserDto
import com.diva.models.api.user.dtos.UserEmailDto
import com.diva.models.api.user.dtos.EmailTokenDto
import com.diva.models.api.user.response.UserResponse
import io.github.juevigrace.diva.core.DivaResult
import io.github.juevigrace.diva.core.errors.DivaError

interface UserNetworkClient {
    suspend fun getAll(
        page: Int,
        pageSize: Int
    ): DivaResult<PaginationResponse<UserResponse>, DivaError.NetworkError>
    suspend fun getById(id: String): DivaResult<UserResponse, DivaError.NetworkError>
    suspend fun create(dto: CreateUserDto, token: String): DivaResult<String, DivaError.NetworkError>
    suspend fun update(dto: UpdateUserDto, token: String): DivaResult<Unit, DivaError.NetworkError>
    suspend fun requestEmailUpdate(
        dto: UserEmailDto,
        token: String
    ): DivaResult<Unit, DivaError.NetworkError>
    suspend fun confirmEmailUpdate(
        dto: EmailTokenDto,
        token: String
    ): DivaResult<Unit, DivaError.NetworkError>
    suspend fun updateEmail(
        dto: UserEmailDto,
        token: String
    ): DivaResult<Unit, DivaError.NetworkError>
    suspend fun delete(token: String): DivaResult<Unit, DivaError.NetworkError>
}
