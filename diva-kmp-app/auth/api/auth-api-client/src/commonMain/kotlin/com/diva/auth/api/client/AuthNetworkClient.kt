package com.diva.auth.api.client

import com.diva.models.auth.dtos.SignInDto
import com.diva.models.auth.response.SessionResponse
import io.github.juevigrace.diva.core.models.DivaError
import io.github.juevigrace.diva.core.models.DivaResult

interface AuthNetworkClient {
    suspend fun signIn(dto: SignInDto): DivaResult<SessionResponse, DivaError>
    suspend fun signUp(dto: SignInDto): DivaResult<SessionResponse, DivaError>
    suspend fun signOut(): DivaResult<Nothing, DivaError>
    suspend fun ping(): DivaResult<Nothing, DivaError>
    suspend fun refresh(dto: SignInDto): DivaResult<SessionResponse, DivaError>
    suspend fun passwordResetRequest(dto: SignInDto): DivaResult<Nothing, DivaError>
    suspend fun passwordResetConfirm(dto: SignInDto): DivaResult<Nothing, DivaError>
    suspend fun passwordReset(dto: SignInDto): DivaResult<Nothing, DivaError>
}
