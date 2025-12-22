package com.diva.auth.data

import com.diva.models.Repository
import com.diva.models.auth.dtos.PasswordResetConfirmDto
import com.diva.models.auth.dtos.PasswordResetRequestDto
import com.diva.models.auth.dtos.SessionDataDto
import com.diva.models.auth.dtos.SignInDto
import io.github.juevigrace.diva.core.models.DivaError
import io.github.juevigrace.diva.core.models.DivaResult
import kotlinx.coroutines.flow.Flow

interface AuthRepository : Repository {
    suspend fun signIn(dto: SignInDto): Flow<DivaResult<Unit, DivaError>>
    suspend fun signUp(dto: SignInDto): Flow<DivaResult<Unit, DivaError>>
    suspend fun signOut(): Flow<DivaResult<Unit, DivaError>>
    suspend fun ping(): Flow<DivaResult<Unit, DivaError>>
    suspend fun refresh(dto: SessionDataDto): Flow<DivaResult<Unit, DivaError>>
    suspend fun passwordResetRequest(dto: PasswordResetRequestDto): Flow<DivaResult<Unit, DivaError>>
    suspend fun passwordResetConfirm(dto: PasswordResetConfirmDto): Flow<DivaResult<Unit, DivaError>>
    suspend fun passwordReset(dto: PasswordResetRequestDto): Flow<DivaResult<Unit, DivaError>>
}
