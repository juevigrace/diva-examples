package com.diva.auth.data

import com.diva.models.api.ApiResponse
import com.diva.models.api.auth.dtos.PasswordUpdateDto
import com.diva.models.api.auth.dtos.SessionDataDto
import com.diva.models.api.auth.dtos.SignInDto
import com.diva.models.api.auth.dtos.SignUpDto
import com.diva.models.api.auth.response.SessionResponse
import com.diva.models.api.user.dtos.CreateUserDto
import com.diva.models.auth.Session
import io.github.juevigrace.diva.core.models.DivaError
import io.github.juevigrace.diva.core.models.DivaResult
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

interface AuthService {
    @OptIn(ExperimentalUuidApi::class)
    suspend fun passwordReset(
        dto: PasswordUpdateDto,
        onUpdatePassword: suspend (passwordHash: String) -> DivaResult<Unit, DivaError>
    ): DivaResult<ApiResponse<Nothing>, DivaError.NetworkError>

    @OptIn(ExperimentalUuidApi::class)
    suspend fun ping(sessionId: Uuid): DivaResult<ApiResponse<Nothing>, DivaError.NetworkError>

    suspend fun refresh(
        dto: SessionDataDto,
        session: Session
    ): DivaResult<ApiResponse<SessionResponse>, DivaError.NetworkError>
    suspend fun signIn(dto: SignInDto): DivaResult<ApiResponse<SessionResponse>, DivaError.NetworkError>

    @OptIn(ExperimentalUuidApi::class)
    suspend fun signUp(
        dto: SignUpDto,
        onCreateUser: suspend (dto: CreateUserDto) -> DivaResult<Uuid, DivaError>
    ): DivaResult<ApiResponse<SessionResponse>, DivaError.NetworkError>

    @OptIn(ExperimentalUuidApi::class)
    suspend fun signOut(sessionId: Uuid): DivaResult<ApiResponse<Nothing>, DivaError.NetworkError>

    suspend fun verifyUser(
        onVerify: suspend () -> DivaResult<Unit, DivaError>,
        onVerified: suspend () -> DivaResult<Unit, DivaError>
    ): DivaResult<ApiResponse<Nothing>, DivaError>
}
