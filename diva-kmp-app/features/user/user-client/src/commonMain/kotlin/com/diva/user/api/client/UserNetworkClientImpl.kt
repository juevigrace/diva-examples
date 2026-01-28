package com.diva.user.api.client

import com.diva.models.api.ApiResponse
import com.diva.models.api.PaginationResponse
import com.diva.models.api.user.dtos.CreateUserDto
import com.diva.models.api.user.dtos.EmailTokenDto
import com.diva.models.api.user.dtos.UpdateUserDto
import com.diva.models.api.user.dtos.UserEmailDto
import com.diva.models.api.user.response.UserResponse
import io.github.juevigrace.diva.core.DivaResult
import io.github.juevigrace.diva.core.errors.DivaError
import io.github.juevigrace.diva.core.errors.asNetworkError
import io.github.juevigrace.diva.core.errors.toDivaError
import io.github.juevigrace.diva.core.flatMap
import io.github.juevigrace.diva.core.network.HttpRequestMethod
import io.github.juevigrace.diva.core.tryResult
import io.github.juevigrace.diva.network.client.DivaClient
import io.github.juevigrace.diva.network.client.delete
import io.github.juevigrace.diva.network.client.get
import io.github.juevigrace.diva.network.client.patch
import io.github.juevigrace.diva.network.client.post
import io.github.juevigrace.diva.network.client.put
import io.github.juevigrace.diva.network.client.toHttpStatusCodes
import io.ktor.client.call.body
import io.ktor.http.HttpStatusCode

class UserNetworkClientImpl(
    private val client: DivaClient
) : UserNetworkClient {
    override suspend fun getAll(
        page: Int,
        pageSize: Int,
    ): DivaResult<PaginationResponse<UserResponse>, DivaError.NetworkError> {
        return tryResult(
            onError = { e -> e.toDivaError().asNetworkError(HttpRequestMethod.GET, "/api/user") }
        ) {
            client.get(path = "/api/user").flatMap { response ->
                val body: ApiResponse<PaginationResponse<UserResponse>> = response.body()
                when (response.status) {
                    HttpStatusCode.OK -> body.data?.let { data -> DivaResult.success(data) }
                        ?: DivaResult.failure(
                            DivaError.NetworkError(
                                method = HttpRequestMethod.GET,
                                url = "/api/user",
                                statusCode = response.status.toHttpStatusCodes(),
                                details = "Data is null: ${body.message}",
                            )
                        )
                    else -> {
                        DivaResult.failure(
                            DivaError.NetworkError(
                                method = HttpRequestMethod.GET,
                                url = "/api/user",
                                statusCode = response.status.toHttpStatusCodes(),
                                details = body.message,
                            )
                        )
                    }
                }
            }
        }
    }

    override suspend fun getById(id: String): DivaResult<UserResponse, DivaError.NetworkError> {
        return tryResult(
            onError = { e -> e.toDivaError().asNetworkError(HttpRequestMethod.GET, "/api/user/{id}") }
        ) {
            client.get(path = "/api/user/$id").flatMap { response ->
                val body: ApiResponse<UserResponse> = response.body()
                when (response.status) {
                    HttpStatusCode.OK -> body.data?.let { data -> DivaResult.success(data) }
                        ?: DivaResult.failure(
                            DivaError.NetworkError(
                                method = HttpRequestMethod.GET,
                                url = "/api/user/{id}",
                                statusCode = response.status.toHttpStatusCodes(),
                                details = "Data is null: ${body.message}",
                            )
                        )
                    else -> {
                        DivaResult.failure(
                            DivaError.NetworkError(
                                method = HttpRequestMethod.GET,
                                url = "/api/user/{id}",
                                statusCode = response.status.toHttpStatusCodes(),
                                details = body.message,
                            )
                        )
                    }
                }
            }
        }
    }

    override suspend fun create(
        dto: CreateUserDto,
        token: String
    ): DivaResult<String, DivaError.NetworkError> {
        return tryResult(
            onError = { e -> e.toDivaError().asNetworkError(HttpRequestMethod.POST, "/api/user") }
        ) {
            client.post(
                path = "/api/user",
                body = dto,
                headers = mapOf("Authorization" to "Bearer $token")
            ).flatMap { response ->
                val body: ApiResponse<String> = response.body()
                when (response.status) {
                    HttpStatusCode.Created -> body.data?.let { data -> DivaResult.success(data) }
                        ?: DivaResult.failure(
                            DivaError.NetworkError(
                                method = HttpRequestMethod.POST,
                                url = "/api/user",
                                statusCode = response.status.toHttpStatusCodes(),
                                details = "Data is null: ${body.message}",
                            )
                        )
                    else -> {
                        DivaResult.failure(
                            DivaError.NetworkError(
                                method = HttpRequestMethod.POST,
                                url = "/api/user",
                                statusCode = response.status.toHttpStatusCodes(),
                                details = body.message,
                            )
                        )
                    }
                }
            }
        }
    }

    override suspend fun requestEmailUpdate(
        dto: UserEmailDto,
        token: String,
    ): DivaResult<Unit, DivaError.NetworkError> {
        return tryResult(
            onError = { e -> e.toDivaError().asNetworkError(HttpRequestMethod.POST, "/api/user/update/email/request") }
        ) {
            client.post(
                path = "/api/user/update/email/request",
                body = dto,
                headers = mapOf("Authorization" to "Bearer $token")
            ).flatMap { response ->
                val body: ApiResponse<Nothing> = response.body()
                when (response.status) {
                    HttpStatusCode.OK -> DivaResult.success(Unit)
                    else -> {
                        DivaResult.failure(
                            DivaError.NetworkError(
                                method = HttpRequestMethod.POST,
                                url = "/api/user/update/email/request",
                                statusCode = response.status.toHttpStatusCodes(),
                                details = body.message,
                            )
                        )
                    }
                }
            }
        }
    }

    override suspend fun confirmEmailUpdate(
        dto: EmailTokenDto,
        token: String,
    ): DivaResult<Unit, DivaError.NetworkError> {
        return tryResult(
            onError = { e -> e.toDivaError().asNetworkError(HttpRequestMethod.POST, "/api/user/update/email/confirm") }
        ) {
            client.post(
                path = "/api/user/update/email/confirm",
                body = dto,
                headers = mapOf("Authorization" to "Bearer $token")
            ).flatMap { response ->
                val body: ApiResponse<Nothing> = response.body()
                when (response.status) {
                    HttpStatusCode.OK -> DivaResult.success(Unit)
                    else -> {
                        DivaResult.failure(
                            DivaError.NetworkError(
                                method = HttpRequestMethod.POST,
                                url = "/api/user/update/email/confirm",
                                statusCode = response.status.toHttpStatusCodes(),
                                details = body.message,
                            )
                        )
                    }
                }
            }
        }
    }

    override suspend fun update(
        dto: UpdateUserDto,
        token: String
    ): DivaResult<Unit, DivaError.NetworkError> {
        return tryResult(
            onError = { e -> e.toDivaError().asNetworkError(HttpRequestMethod.PUT, "/api/user") }
        ) {
            client.put(
                path = "/api/user",
                body = dto,
                headers = mapOf("Authorization" to "Bearer $token")
            ).flatMap { response ->
                val body: ApiResponse<Nothing> = response.body()
                when (response.status) {
                    HttpStatusCode.Accepted -> DivaResult.success(Unit)
                    else -> {
                        DivaResult.failure(
                            DivaError.NetworkError(
                                method = HttpRequestMethod.PUT,
                                url = "/api/user",
                                statusCode = response.status.toHttpStatusCodes(),
                                details = body.message,
                            )
                        )
                    }
                }
            }
        }
    }

    override suspend fun updateEmail(
        dto: UserEmailDto,
        token: String,
    ): DivaResult<Unit, DivaError.NetworkError> {
        return tryResult(
            onError = { e -> e.toDivaError().asNetworkError(HttpRequestMethod.PATCH, "/api/user/update/email") }
        ) {
            client.patch(
                path = "/api/user/update/email",
                body = dto,
                headers = mapOf("Authorization" to "Bearer $token")
            ).flatMap { response ->
                val body: ApiResponse<Nothing> = response.body()
                when (response.status) {
                    HttpStatusCode.Accepted -> DivaResult.success(Unit)
                    else -> {
                        DivaResult.failure(
                            DivaError.NetworkError(
                                method = HttpRequestMethod.PATCH,
                                url = "/api/user/update/email",
                                statusCode = response.status.toHttpStatusCodes(),
                                details = body.message,
                            )
                        )
                    }
                }
            }
        }
    }

    override suspend fun delete(token: String): DivaResult<Unit, DivaError.NetworkError> {
        return tryResult(
            onError = { e -> e.toDivaError().asNetworkError(HttpRequestMethod.DELETE, "/api/user") }
        ) {
            client.delete(
                path = "/api/user",
                headers = mapOf("Authorization" to "Bearer $token")
            ).flatMap { response ->
                val body: ApiResponse<Nothing> = response.body()
                when (response.status) {
                    HttpStatusCode.NoContent -> DivaResult.success(Unit)
                    else -> {
                        DivaResult.failure(
                            DivaError.NetworkError(
                                method = HttpRequestMethod.PATCH,
                                url = "/api/user",
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
