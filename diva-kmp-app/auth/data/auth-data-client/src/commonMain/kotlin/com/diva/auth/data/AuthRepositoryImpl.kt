package com.diva.auth.data

import com.diva.auth.api.client.AuthNetworkClient
import com.diva.models.auth.dtos.PasswordResetConfirmDto
import com.diva.models.auth.dtos.PasswordResetRequestDto
import com.diva.models.auth.dtos.SessionDataDto
import com.diva.models.auth.dtos.SignInDto
import com.diva.session.database.shared.SessionStorage
import io.github.juevigrace.diva.core.models.DivaError
import io.github.juevigrace.diva.core.models.DivaResult
import kotlinx.coroutines.flow.Flow

class AuthRepositoryImpl(
    private val sessionStorage: SessionStorage,
    private val authClient: AuthNetworkClient,
) : AuthRepository {
    override suspend fun signIn(dto: SignInDto): Flow<DivaResult<Unit, DivaError>> {
        TODO("Not yet implemented")
    }

    override suspend fun signUp(dto: SignInDto): Flow<DivaResult<Unit, DivaError>> {
        TODO("Not yet implemented")
    }

    override suspend fun signOut(): Flow<DivaResult<Unit, DivaError>> {
        TODO("Not yet implemented")
    }

    override suspend fun ping(): Flow<DivaResult<Unit, DivaError>> {
        TODO("Not yet implemented")
    }

    override suspend fun refresh(dto: SessionDataDto): Flow<DivaResult<Unit, DivaError>> {
        TODO("Not yet implemented")
    }

    override suspend fun passwordResetRequest(dto: PasswordResetRequestDto): Flow<DivaResult<Unit, DivaError>> {
        TODO("Not yet implemented")
    }

    override suspend fun passwordResetConfirm(dto: PasswordResetConfirmDto): Flow<DivaResult<Unit, DivaError>> {
        TODO("Not yet implemented")
    }

    override suspend fun passwordReset(dto: PasswordResetRequestDto): Flow<DivaResult<Unit, DivaError>> {
        TODO("Not yet implemented")
    }
}
