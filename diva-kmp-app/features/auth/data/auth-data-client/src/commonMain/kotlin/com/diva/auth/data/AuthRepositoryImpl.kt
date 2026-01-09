package com.diva.auth.data

import com.diva.auth.api.client.AuthNetworkClient
import com.diva.models.api.auth.dtos.SessionDataDto
import com.diva.models.api.auth.dtos.SignInDto
import com.diva.session.database.shared.SessionStorage
import io.github.juevigrace.diva.core.DivaResult
import io.github.juevigrace.diva.core.errors.DivaError
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

    override suspend fun forgotPasswordRequest(email: String): Flow<DivaResult<Unit, DivaError>> {
        TODO("Not yet implemented")
    }

    override suspend fun forgotPasswordConfirm(token: String): Flow<DivaResult<Unit, DivaError>> {
        TODO("Not yet implemented")
    }

    override suspend fun forgotPasswordReset(newPassword: String): Flow<DivaResult<Unit, DivaError>> {
        TODO("Not yet implemented")
    }
}
