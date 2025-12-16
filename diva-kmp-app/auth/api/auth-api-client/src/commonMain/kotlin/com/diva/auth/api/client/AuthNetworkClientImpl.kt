package com.diva.auth.api.client

import com.diva.auth.models.api.dtos.SignInDto
import com.diva.auth.models.api.response.AuthResponse
import com.diva.models.ApiResponse
import io.github.juevigrace.diva.core.models.DivaError
import io.github.juevigrace.diva.core.models.DivaErrorException
import io.github.juevigrace.diva.core.models.DivaResult
import io.github.juevigrace.diva.core.models.flatMap
import io.github.juevigrace.diva.core.models.tryResult
import io.github.juevigrace.diva.network.client.NetworkClient
import io.github.juevigrace.diva.network.client.post
import io.ktor.client.call.body
import io.ktor.http.HttpStatusCode

class AuthNetworkClientImpl(private val client: NetworkClient) : AuthNetworkClient {
    override suspend fun signIn(dto: SignInDto): DivaResult<AuthResponse, DivaError> {
        return client.post("/api/auth/signIn", dto).flatMap { res ->
            tryResult(
                onError = { e ->
                    if (e is DivaErrorException) {
                        e.divaError
                    } else {
                        DivaError.Companion.exception(
                            e = e,
                            origin = "AuthNetworkClient.signIn",
                        )
                    }
                },
            ) {
                val body: ApiResponse<AuthResponse> = res.body()
                when (res.status) {
                    HttpStatusCode.Companion.OK -> {
                        DivaResult.Companion.success(body.data!!)
                    }

                    else -> {
                        throw DivaErrorException(
                            DivaError.Companion.network(
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
}
