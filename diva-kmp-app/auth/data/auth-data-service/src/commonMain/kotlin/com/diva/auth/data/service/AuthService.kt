package com.diva.auth.data.service

import com.diva.models.ApiResponse
import com.diva.models.auth.Session
import com.diva.models.auth.dtos.PasswordResetConfirmDto
import com.diva.models.auth.dtos.PasswordResetRequestDto
import com.diva.models.auth.dtos.SessionDataDto
import com.diva.models.auth.dtos.SignInDto
import com.diva.models.auth.response.SessionResponse
import io.github.juevigrace.diva.core.models.DivaError
import io.github.juevigrace.diva.core.models.DivaResult
import java.util.UUID

// TODO: implement search as callbacks better?
interface AuthService {
    suspend fun signIn(dto: SignInDto): DivaResult<ApiResponse<SessionResponse>, DivaError.NetworkError>
    suspend fun signUp(dto: SignInDto): DivaResult<ApiResponse<SessionResponse>, DivaError.NetworkError>
    suspend fun signOut(sessionId: UUID): DivaResult<ApiResponse<Nothing>, DivaError.NetworkError>
    suspend fun ping(sessionId: UUID): DivaResult<ApiResponse<Nothing>, DivaError.NetworkError>
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
    suspend fun passwordReset(dto: PasswordResetRequestDto): DivaResult<ApiResponse<Nothing>, DivaError.NetworkError>
}
