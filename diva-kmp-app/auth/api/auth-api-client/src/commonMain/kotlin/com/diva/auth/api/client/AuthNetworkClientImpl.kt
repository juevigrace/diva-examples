package com.diva.auth.api.client

import com.diva.models.api.ApiResponse
import com.diva.models.api.auth.dtos.SignInDto
import com.diva.models.api.auth.response.SessionResponse
import io.github.juevigrace.diva.core.models.DivaError
import io.github.juevigrace.diva.core.models.DivaErrorException
import io.github.juevigrace.diva.core.models.DivaResult
import io.github.juevigrace.diva.core.models.flatMap
import io.github.juevigrace.diva.core.models.tryResult
import io.github.juevigrace.diva.network.client.DivaClient
import io.github.juevigrace.diva.network.client.post
import io.ktor.client.call.body
import io.ktor.http.HttpStatusCode

class AuthNetworkClientImpl(private val client: DivaClient) : AuthNetworkClient {
    override suspend fun signIn(dto: SignInDto): DivaResult<SessionResponse, DivaError> {
        return client.post("/api/auth/signIn", dto).flatMap { res ->
            tryResult(
                onError = { e ->
                    if (e is DivaErrorException) {
                        e.divaError
                    } else {
                        DivaError.exception(
                            e = e,
                            origin = "AuthNetworkClient.signIn",
                        )
                    }
                },
            ) {
                val body: ApiResponse<SessionResponse> = res.body()
                when (res.status) {
                    HttpStatusCode.OK -> {
                        DivaResult.success(body.data!!)
                    }

                    else -> {
                        throw DivaErrorException(
                            DivaError.network(
                                operation = "POST SIGN IN",
                                url = "/api/auth/signIn",
                                statusCode = res.status.value,
                                details = body.message,
                            )
                        )
                    }
                }
            }
        }
    }

    override suspend fun signUp(dto: SignInDto): DivaResult<SessionResponse, DivaError> {
        TODO("Not yet implemented")
    }

    override suspend fun signOut(): DivaResult<Nothing, DivaError> {
        TODO("Not yet implemented")
    }

    override suspend fun ping(): DivaResult<Nothing, DivaError> {
        TODO("Not yet implemented")
    }

    override suspend fun refresh(dto: SignInDto): DivaResult<SessionResponse, DivaError> {
        TODO("Not yet implemented")
    }

    override suspend fun passwordResetRequest(dto: SignInDto): DivaResult<Nothing, DivaError> {
        TODO("Not yet implemented")
    }

    override suspend fun passwordResetConfirm(dto: SignInDto): DivaResult<Nothing, DivaError> {
        TODO("Not yet implemented")
    }

    override suspend fun passwordReset(dto: SignInDto): DivaResult<Nothing, DivaError> {
        TODO("Not yet implemented")
    }
}
