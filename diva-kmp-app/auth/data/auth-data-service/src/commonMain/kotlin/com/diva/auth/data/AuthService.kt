package com.diva.auth.data

import com.diva.models.api.ApiResponse
import com.diva.models.api.auth.dtos.PasswordResetConfirmDto
import com.diva.models.api.auth.dtos.PasswordResetRequestDto
import com.diva.models.api.auth.dtos.SessionDataDto
import com.diva.models.api.auth.dtos.SignInDto
import com.diva.models.api.auth.response.SessionResponse
import com.diva.models.api.user.dtos.CreateUserDto
import com.diva.models.auth.Session
import io.github.juevigrace.diva.core.models.DivaError
import io.github.juevigrace.diva.core.models.DivaResult
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

interface AuthService {
    suspend fun signIn(dto: SignInDto): DivaResult<ApiResponse<SessionResponse>, DivaError.NetworkError>

    @OptIn(ExperimentalUuidApi::class)
    suspend fun signUp(
        dto: CreateUserDto,
        onCreateUser: suspend (CreateUserDto) -> DivaResult<Uuid, DivaError>
    ): DivaResult<ApiResponse<SessionResponse>, DivaError.NetworkError>

    @OptIn(ExperimentalUuidApi::class)
    suspend fun signOut(sessionId: Uuid): DivaResult<ApiResponse<Nothing>, DivaError.NetworkError>

    @OptIn(ExperimentalUuidApi::class)
    suspend fun ping(sessionId: Uuid): DivaResult<ApiResponse<Nothing>, DivaError.NetworkError>

    suspend fun refresh(
        dto: SessionDataDto,
        session: Session
    ): DivaResult<ApiResponse<SessionResponse>, DivaError.NetworkError>

    suspend fun passwordResetRequest(
        dto: PasswordResetRequestDto
    ): DivaResult<ApiResponse<Nothing>, DivaError.NetworkError>

    suspend fun passwordResetConfirm(
        dto: PasswordResetConfirmDto
    ): DivaResult<ApiResponse<Nothing>, DivaError.NetworkError>

    @OptIn(ExperimentalUuidApi::class)
    suspend fun passwordReset(
        dto: PasswordResetRequestDto,
        onUpdatePassword: suspend (userId: Uuid, passwordHash: String) -> DivaResult<Unit, DivaError>
    ): DivaResult<ApiResponse<Nothing>, DivaError.NetworkError>
}
