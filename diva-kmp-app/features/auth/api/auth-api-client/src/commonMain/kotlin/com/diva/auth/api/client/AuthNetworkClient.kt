package com.diva.auth.api.client

import com.diva.models.api.auth.dtos.PasswordUpdateDto
import com.diva.models.api.auth.dtos.SignInDto
import com.diva.models.api.auth.response.SessionResponse
import com.diva.models.api.user.dtos.EmailTokenDto
import com.diva.models.api.user.dtos.UserEmailDto
import io.github.juevigrace.diva.core.DivaResult
import io.github.juevigrace.diva.core.errors.DivaError

interface AuthNetworkClient {
    suspend fun signIn(dto: SignInDto): DivaResult<SessionResponse, DivaError.NetworkError>
    suspend fun signUp(dto: SignInDto): DivaResult<SessionResponse, DivaError.NetworkError>
    suspend fun signOut(token: String): DivaResult<Unit, DivaError.NetworkError>
    suspend fun ping(token: String): DivaResult<Unit, DivaError.NetworkError>
    suspend fun refresh(token: String): DivaResult<SessionResponse, DivaError.NetworkError>
    suspend fun forgotPasswordRequest(dto: UserEmailDto): DivaResult<Unit, DivaError.NetworkError>
    suspend fun forgotPasswordConfirm(dto: EmailTokenDto): DivaResult<Unit, DivaError.NetworkError>
    suspend fun forgotPasswordReset(dto: PasswordUpdateDto, token: String): DivaResult<Unit, DivaError.NetworkError>
}
