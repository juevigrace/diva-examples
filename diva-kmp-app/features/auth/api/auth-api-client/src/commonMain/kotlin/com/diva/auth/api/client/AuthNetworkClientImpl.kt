package com.diva.auth.api.client

import com.diva.models.api.ApiResponse
import com.diva.models.api.auth.dtos.PasswordUpdateDto
import com.diva.models.api.auth.dtos.SessionDataDto
import com.diva.models.api.auth.dtos.SignInDto
import com.diva.models.api.auth.dtos.SignUpDto
import com.diva.models.api.auth.response.SessionResponse
import com.diva.models.api.user.dtos.EmailTokenDto
import com.diva.models.api.user.dtos.UserEmailDto
import io.github.juevigrace.diva.core.DivaResult
import io.github.juevigrace.diva.core.errors.DivaError
import io.github.juevigrace.diva.core.errors.asNetworkError
import io.github.juevigrace.diva.core.errors.toDivaError
import io.github.juevigrace.diva.core.flatMap
import io.github.juevigrace.diva.core.network.HttpRequestMethod
import io.github.juevigrace.diva.core.tryResult
import io.github.juevigrace.diva.network.client.DivaClient
import io.github.juevigrace.diva.network.client.patch
import io.github.juevigrace.diva.network.client.post
import io.github.juevigrace.diva.network.client.toHttpStatusCodes
import io.ktor.client.call.body
import io.ktor.http.HttpStatusCode

class AuthNetworkClientImpl(
    private val client: DivaClient
) : AuthNetworkClient {
    override suspend fun signIn(dto: SignInDto): DivaResult<SessionResponse, DivaError.NetworkError> {
        return tryResult(
            onError = { e -> e.toDivaError().asNetworkError(HttpRequestMethod.POST, "/api/auth/signIn") }
        ) {
            client.post(
                path = "/api/auth/signIn",
                body = dto
            ).flatMap { response ->
                val body: ApiResponse<SessionResponse> = response.body()
                when (response.status) {
                    HttpStatusCode.OK -> {
                        body.data?.let { data -> DivaResult.success(data) }
                            ?: DivaResult.failure(
                                DivaError.NetworkError(
                                    method = HttpRequestMethod.POST,
                                    url = "/api/auth/signIn",
                                    statusCode = response.status.toHttpStatusCodes(),
                                    details = "Data is null: ${body.message}",
                                )
                            )
                    }
                    else -> {
                        DivaResult.failure(
                            DivaError.NetworkError(
                                method = HttpRequestMethod.POST,
                                url = "/api/auth/signIn",
                                statusCode = response.status.toHttpStatusCodes(),
                                details = body.message,
                            )
                        )
                    }
                }
            }
        }
    }

    override suspend fun signUp(dto: SignUpDto): DivaResult<SessionResponse, DivaError.NetworkError> {
        return tryResult(
            onError = { e -> e.toDivaError().asNetworkError(HttpRequestMethod.POST, "/api/auth/signUp") }
        ) {
            client.post(
                path = "/api/auth/signUp",
                body = dto
            ).flatMap { response ->
                val body: ApiResponse<SessionResponse> = response.body()
                when (response.status) {
                    HttpStatusCode.Created -> {
                        body.data?.let { data -> DivaResult.success(data) }
                            ?: DivaResult.failure(
                                DivaError.NetworkError(
                                    method = HttpRequestMethod.POST,
                                    url = "/api/auth/signUp",
                                    statusCode = response.status.toHttpStatusCodes(),
                                    details = "Data is null: ${body.message}",
                                )
                            )
                    }
                    else -> {
                        DivaResult.failure(
                            DivaError.NetworkError(
                                method = HttpRequestMethod.POST,
                                url = "/api/auth/signUp",
                                statusCode = response.status.toHttpStatusCodes(),
                                details = body.message,
                            )
                        )
                    }
                }
            }
        }
    }

    override suspend fun signOut(token: String): DivaResult<Unit, DivaError.NetworkError> {
        return tryResult(
            onError = { e -> e.toDivaError().asNetworkError(HttpRequestMethod.POST, "/api/auth/signOut") }
        ) {
            client.post(
                path = "/api/auth/signOut",
                headers = mapOf("Authorization" to "Bearer $token"),
            ).flatMap { response ->
                val body: ApiResponse<Nothing> = response.body()
                when (response.status) {
                    HttpStatusCode.OK -> DivaResult.success(Unit)
                    else -> {
                        DivaResult.failure(
                            DivaError.NetworkError(
                                method = HttpRequestMethod.POST,
                                url = "/api/auth/signOut",
                                statusCode = response.status.toHttpStatusCodes(),
                                details = body.message,
                            )
                        )
                    }
                }
            }
        }
    }

    override suspend fun ping(token: String): DivaResult<Unit, DivaError.NetworkError> {
        return tryResult(
            onError = { e -> e.toDivaError().asNetworkError(HttpRequestMethod.POST, "/api/auth/ping") }
        ) {
            client.post(
                path = "/api/auth/ping",
                headers = mapOf("Authorization" to "Bearer $token"),
            ).flatMap { response ->
                val body: ApiResponse<Nothing> = response.body()
                when (response.status) {
                    HttpStatusCode.OK -> DivaResult.success(Unit)
                    else -> {
                        DivaResult.failure(
                            DivaError.NetworkError(
                                method = HttpRequestMethod.POST,
                                url = "/api/auth/ping",
                                statusCode = response.status.toHttpStatusCodes(),
                                details = body.message,
                            )
                        )
                    }
                }
            }
        }
    }

    override suspend fun refresh(
        dto: SessionDataDto,
        token: String
    ): DivaResult<SessionResponse, DivaError.NetworkError> {
        return tryResult(
            onError = { e -> e.toDivaError().asNetworkError(HttpRequestMethod.POST, "/api/auth/refresh") }
        ) {
            client.post(
                path = "/api/auth/refresh",
                headers = mapOf("Authorization" to "Bearer $token"),
                body = dto,
            ).flatMap { response ->
                val body: ApiResponse<SessionResponse> = response.body()
                when (response.status) {
                    HttpStatusCode.OK -> {
                        body.data?.let { data -> DivaResult.success(data) }
                            ?: DivaResult.failure(
                                DivaError.NetworkError(
                                    method = HttpRequestMethod.POST,
                                    url = "/api/auth/refresh",
                                    statusCode = response.status.toHttpStatusCodes(),
                                    details = "Data is null: ${body.message}",
                                )
                            )
                    }
                    else -> {
                        DivaResult.failure(
                            DivaError.NetworkError(
                                method = HttpRequestMethod.POST,
                                url = "/api/auth/refresh",
                                statusCode = response.status.toHttpStatusCodes(),
                                details = body.message,
                            )
                        )
                    }
                }
            }
        }
    }

    override suspend fun forgotPasswordRequest(dto: UserEmailDto): DivaResult<Unit, DivaError.NetworkError> {
        return tryResult(
            onError = { e ->
                e.toDivaError().asNetworkError(
                    HttpRequestMethod.POST,
                    "/api/auth/forgot/password/request"
                )
            }
        ) {
            client.post(
                path = "/api/auth/forgot/password/request",
                body = dto,
            ).flatMap { response ->
                val body: ApiResponse<Nothing> = response.body()
                when (response.status) {
                    HttpStatusCode.OK -> DivaResult.success(Unit)
                    else -> {
                        DivaResult.failure(
                            DivaError.NetworkError(
                                method = HttpRequestMethod.POST,
                                url = "/api/auth/forgot/password/request",
                                statusCode = response.status.toHttpStatusCodes(),
                                details = body.message,
                            )
                        )
                    }
                }
            }
        }
    }

    override suspend fun forgotPasswordConfirm(dto: EmailTokenDto): DivaResult<Unit, DivaError.NetworkError> {
        return tryResult(
            onError = { e ->
                e.toDivaError().asNetworkError(
                    HttpRequestMethod.POST,
                    "/api/auth/forgot/password/request"
                )
            }
        ) {
            client.post(
                path = "/api/auth/forgot/password/request",
                body = dto,
            ).flatMap { response ->
                val body: ApiResponse<Nothing> = response.body()
                when (response.status) {
                    HttpStatusCode.OK -> DivaResult.success(Unit)
                    else -> {
                        DivaResult.failure(
                            DivaError.NetworkError(
                                method = HttpRequestMethod.POST,
                                url = "/api/auth/forgot/password/request",
                                statusCode = response.status.toHttpStatusCodes(),
                                details = body.message,
                            )
                        )
                    }
                }
            }
        }
    }

    override suspend fun forgotPasswordReset(
        dto: PasswordUpdateDto,
        token: String
    ): DivaResult<Unit, DivaError.NetworkError> {
        return tryResult(
            onError = { e -> e.toDivaError().asNetworkError(HttpRequestMethod.PATCH, "/api/auth/forgot/password") }
        ) {
            client.patch(
                path = "/api/auth/forgot/password",
                body = dto,
                headers = mapOf("Authorization" to "Bearer $token"),
            ).flatMap { response ->
                val body: ApiResponse<Nothing> = response.body()
                when (response.status) {
                    HttpStatusCode.OK -> DivaResult.success(Unit)
                    else -> {
                        DivaResult.failure(
                            DivaError.NetworkError(
                                method = HttpRequestMethod.PATCH,
                                url = "/api/auth/forgot/password",
                                statusCode = response.status.toHttpStatusCodes(),
                                details = body.message,
                            )
                        )
                    }
                }
            }
        }
    }
}
