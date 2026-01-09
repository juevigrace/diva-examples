package com.diva.auth.data

import com.diva.models.Repository
import com.diva.models.api.auth.dtos.SessionDataDto
import com.diva.models.api.auth.dtos.SignInDto
import io.github.juevigrace.diva.core.DivaResult
import io.github.juevigrace.diva.core.errors.DivaError
import kotlinx.coroutines.flow.Flow

interface AuthRepository : Repository {
    suspend fun signIn(dto: SignInDto): Flow<DivaResult<Unit, DivaError>>
    suspend fun signUp(dto: SignInDto): Flow<DivaResult<Unit, DivaError>>
    suspend fun signOut(): Flow<DivaResult<Unit, DivaError>>
    suspend fun ping(): Flow<DivaResult<Unit, DivaError>>
    suspend fun refresh(dto: SessionDataDto): Flow<DivaResult<Unit, DivaError>>
    suspend fun forgotPasswordRequest(email: String): Flow<DivaResult<Unit, DivaError>>
    suspend fun forgotPasswordConfirm(token: String): Flow<DivaResult<Unit, DivaError>>
    suspend fun forgotPasswordReset(newPassword: String): Flow<DivaResult<Unit, DivaError>>
}
