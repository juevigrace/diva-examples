package com.diva.auth.data

import com.diva.auth.api.client.AuthNetworkClient
import com.diva.models.api.auth.dtos.PasswordUpdateDto
import com.diva.models.api.auth.dtos.SessionDataDto
import com.diva.models.api.auth.dtos.SignInDto
import com.diva.models.api.auth.dtos.SignUpDto
import com.diva.models.api.user.dtos.EmailTokenDto
import com.diva.models.api.user.dtos.UserEmailDto
import com.diva.models.auth.Session
import com.diva.models.auth.SignInForm
import com.diva.models.auth.SignUpForm
import com.diva.session.database.shared.SessionStorage
import io.github.juevigrace.diva.core.DivaResult
import io.github.juevigrace.diva.core.errors.DivaError
import io.github.juevigrace.diva.core.onFailure
import io.github.juevigrace.diva.core.onSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlin.time.ExperimentalTime
import kotlin.uuid.ExperimentalUuidApi

class AuthRepositoryImpl(
    private val sessionStorage: SessionStorage,
    private val authClient: AuthNetworkClient,
) : AuthRepository {
    @OptIn(ExperimentalTime::class, ExperimentalUuidApi::class)
    override fun signIn(form: SignInForm): Flow<DivaResult<Unit, DivaError>> {
        return flow {
            authClient.signIn(form.toSignInDto())
                .onFailure { err -> emit(DivaResult.failure(err)) }
                .onSuccess { res ->
                    sessionStorage
                        .insert(Session.fromResponse(res))
                        .onFailure { err -> emit(DivaResult.failure(err)) }
                        .onSuccess { emit(DivaResult.success(Unit)) }
                }
        }.flowOn(Dispatchers.Default)
    }

    override fun signUp(form: SignUpForm): Flow<DivaResult<Unit, DivaError>> {
        return flow {
            authClient.signUp(form.toSignUpDto())
                .onFailure { err -> emit(DivaResult.failure(err)) }
                .onSuccess { res ->
                    sessionStorage
                        .insert(Session.fromResponse(res))
                        .onFailure { err -> emit(DivaResult.failure(err)) }
                        .onSuccess { emit(DivaResult.success(Unit)) }
                }
        }.flowOn(Dispatchers.Default)
    }

    @OptIn(ExperimentalUuidApi::class)
    override fun signOut(): Flow<DivaResult<Unit, DivaError>> {
        return withSession(sessionStorage::getCurrentSession) { value ->
            authClient.signOut(value.accessToken)
                .onFailure { err -> emit(DivaResult.failure(err)) }
                .onSuccess {
                    sessionStorage.delete(value.id)
                        .onFailure { err -> emit(DivaResult.failure(err)) }
                        .onSuccess { emit(DivaResult.success(Unit)) }
                }
        }
    }

    override fun ping(): Flow<DivaResult<Unit, DivaError>> {
        return withSession(sessionStorage::getCurrentSession) { value ->
            authClient.ping(value.accessToken)
                // TODO: this should refresh if fails for the access token
                .onFailure { err -> emit(DivaResult.failure(err)) }
                .onSuccess { emit(DivaResult.success(Unit)) }
        }
    }

    override fun refresh(device: String): Flow<DivaResult<Unit, DivaError>> {
        return withSession(sessionStorage::getCurrentSession) { value ->
            authClient.refresh(SessionDataDto(device), value.refreshToken)
                .onFailure { err -> emit(DivaResult.failure(err)) }
                .onSuccess { res ->
                    sessionStorage
                        .insert(Session.fromResponse(res))
                        .onFailure { err -> emit(DivaResult.failure(err)) }
                        .onSuccess { emit(DivaResult.success(Unit)) }
                }
        }
    }

    // TODO: handle session if it returns it for the following functions
    override fun forgotPasswordRequest(email: String): Flow<DivaResult<Unit, DivaError>> {
        return flow {
            authClient.forgotPasswordRequest(UserEmailDto(email))
                .onFailure { err -> emit(DivaResult.failure(err)) }
                .onSuccess { emit(DivaResult.success(Unit)) }
        }.flowOn(Dispatchers.Default)
    }

    override fun forgotPasswordConfirm(token: String): Flow<DivaResult<Unit, DivaError>> {
        return flow {
            authClient.forgotPasswordConfirm(EmailTokenDto(token))
                .onFailure { err -> emit(DivaResult.failure(err)) }
                .onSuccess { emit(DivaResult.success(Unit)) }
        }.flowOn(Dispatchers.Default)
    }

    override fun forgotPasswordReset(newPassword: String): Flow<DivaResult<Unit, DivaError>> {
        return withSession(sessionStorage::getCurrentSession) { value ->
            authClient.forgotPasswordReset(PasswordUpdateDto(newPassword), value.accessToken)
                .onFailure { err -> emit(DivaResult.failure(err)) }
                .onSuccess { emit(DivaResult.success(Unit)) }
        }
    }
}
