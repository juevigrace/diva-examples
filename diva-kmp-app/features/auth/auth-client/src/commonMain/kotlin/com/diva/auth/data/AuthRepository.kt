package com.diva.auth.data

import com.diva.models.Repository
import com.diva.models.auth.SignInForm
import com.diva.models.auth.SignUpForm
import io.github.juevigrace.diva.core.DivaResult
import io.github.juevigrace.diva.core.errors.DivaError
import kotlinx.coroutines.flow.Flow

interface AuthRepository : Repository {
    fun signIn(form: SignInForm): Flow<DivaResult<Unit, DivaError>>
    fun signUp(form: SignUpForm): Flow<DivaResult<Unit, DivaError>>
    fun signOut(): Flow<DivaResult<Unit, DivaError>>
    fun ping(): Flow<DivaResult<Unit, DivaError>>
    fun refresh(device: String): Flow<DivaResult<Unit, DivaError>>
    fun forgotPasswordRequest(email: String): Flow<DivaResult<Unit, DivaError>>
    fun forgotPasswordConfirm(token: String): Flow<DivaResult<Unit, DivaError>>
    fun forgotPasswordReset(newPassword: String): Flow<DivaResult<Unit, DivaError>>
}
