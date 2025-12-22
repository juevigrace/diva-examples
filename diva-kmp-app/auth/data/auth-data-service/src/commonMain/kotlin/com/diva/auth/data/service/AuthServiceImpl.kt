package com.diva.auth.data.service

import com.diva.models.ApiResponse
import com.diva.models.auth.Session
import com.diva.models.auth.dtos.PasswordResetConfirmDto
import com.diva.models.auth.dtos.PasswordResetRequestDto
import com.diva.models.auth.dtos.SessionDataDto
import com.diva.models.auth.dtos.SignInDto
import com.diva.models.auth.response.SessionResponse
import com.diva.session.database.shared.SessionStorage
import io.github.juevigrace.diva.core.models.DivaError
import io.github.juevigrace.diva.core.models.DivaResult
import io.github.juevigrace.diva.core.models.toDivaError
import io.github.juevigrace.diva.core.models.tryResult
import java.util.UUID

class AuthServiceImpl(
    private val sessionStorage: SessionStorage,
    private val userStorage: SessionStorage
) : AuthService {
    override suspend fun signIn(dto: SignInDto): DivaResult<ApiResponse<SessionResponse>, DivaError.NetworkError> {
        return tryResult(
            onError = { e ->
                val err = e.toDivaError("AuthServiceImpl.signIn")
                if (err is DivaError.NetworkError) err else
                DivaError.network(
                    operation = "Sign in",
                    url = "/auth/signIn",
                    statusCode = 500,
                    details = err.message,
                    cause = err
                )
            }
        ) {
            DivaResult.success(
                ApiResponse(
                    data = SessionResponse(
                        sessionId = "",
                        userId = "",
                        accessToken = "",
                        refreshToken = "",
                    ),
                    message = "success"
                )
            )
        }
    }

    override suspend fun signUp(dto: SignInDto): DivaResult<ApiResponse<SessionResponse>, DivaError.NetworkError> {
        TODO("Not yet implemented")
    }

    override suspend fun signOut(sessionId: UUID): DivaResult<ApiResponse<Nothing>, DivaError.NetworkError> {
        TODO("Not yet implemented")
    }

    override suspend fun ping(sessionId: UUID): DivaResult<ApiResponse<Nothing>, DivaError.NetworkError> {
        TODO("Not yet implemented")
    }

    override suspend fun refresh(
        dto: SessionDataDto,
        session: Session
    ): DivaResult<ApiResponse<SessionResponse>, DivaError.NetworkError> {
        TODO("Not yet implemented")
    }

    override suspend fun passwordResetRequest(
        dto: PasswordResetRequestDto
    ): DivaResult<ApiResponse<Nothing>, DivaError.NetworkError> {
        TODO("Not yet implemented")
    }

    override suspend fun passwordResetConfirm(
        dto: PasswordResetConfirmDto
    ): DivaResult<ApiResponse<Nothing>, DivaError.NetworkError> {
        TODO("Not yet implemented")
    }

    override suspend fun passwordReset(dto: PasswordResetRequestDto): DivaResult<ApiResponse<Nothing>, DivaError.NetworkError> {
        TODO("Not yet implemented")
    }
}
