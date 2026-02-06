package com.diva.social.api.client.interaction

import com.diva.models.api.ApiResponse
import com.diva.models.api.PaginationResponse
import com.diva.models.api.social.interaction.dtos.CreateInteractionDto
import com.diva.models.api.social.interaction.dtos.UpdateInteractionDto
import com.diva.models.api.social.interaction.response.InteractionResponse
import io.github.juevigrace.diva.core.DivaResult
import io.github.juevigrace.diva.core.Option
import io.github.juevigrace.diva.core.errors.DivaError
import io.github.juevigrace.diva.core.errors.ErrorCause
import io.github.juevigrace.diva.core.errors.toDivaError
import io.github.juevigrace.diva.core.flatMap
import io.github.juevigrace.diva.core.network.HttpRequestMethod
import io.github.juevigrace.diva.core.tryResult
import io.github.juevigrace.diva.network.client.DivaClient
import io.github.juevigrace.diva.network.client.delete
import io.github.juevigrace.diva.network.client.get
import io.github.juevigrace.diva.network.client.post
import io.github.juevigrace.diva.network.client.put
import io.github.juevigrace.diva.network.client.toHttpStatusCodes
import io.ktor.http.HttpStatusCode

interface InteractionClient {
    suspend fun getAll(
        page: Int,
        pageSize: Int
    ): DivaResult<PaginationResponse<InteractionResponse>, DivaError>

    suspend fun getById(id: String): DivaResult<InteractionResponse, DivaError>

    suspend fun create(dto: CreateInteractionDto, token: String): DivaResult<InteractionResponse, DivaError>

    suspend fun update(
        id: String,
        dto: UpdateInteractionDto,
        token: String
    ): DivaResult<InteractionResponse, DivaError>

    suspend fun delete(id: String, token: String): DivaResult<Unit, DivaError>
}

class InteractionClientImpl(
    private val client: DivaClient
) : InteractionClient {
    override suspend fun getAll(
        page: Int,
        pageSize: Int
    ): DivaResult<PaginationResponse<InteractionResponse>, DivaError> {
        return tryResult(
            onError = { e -> e.toDivaError() }
        ) {
            client.get(path = "/api/interaction").flatMap { response ->
                val body: ApiResponse<PaginationResponse<InteractionResponse>> = response.body()
                when (response.status) {
                    HttpStatusCode.OK -> {
                        body.data?.let { data -> DivaResult.success(data) }
                            ?: DivaResult.failure(
                                DivaError(
                                    cause = ErrorCause.Validation.MissingValue("data", Option.Some(body.message)),
                                )
                            )
                    }
                    else -> {
                        DivaResult.failure(
                            DivaError(
                                cause = ErrorCause.Network.Error(
                                    method = HttpRequestMethod.GET,
                                    url = "/api/interaction",
                                    status = response.status.toHttpStatusCodes(),
                                    details = Option.Some(body.message)
                                )
                            )
                        )
                    }
                }
            }
        }
    }

    override suspend fun getById(id: String): DivaResult<InteractionResponse, DivaError> {
        return tryResult(
            onError = { e -> e.toDivaError() }
        ) {
            client.get(path = "/api/interaction/$id").flatMap { response ->
                val body: ApiResponse<InteractionResponse> = response.body()
                when (response.status) {
                    HttpStatusCode.OK -> {
                        body.data?.let { data -> DivaResult.success(data) }
                            ?: DivaResult.failure(
                                DivaError(
                                    cause = ErrorCause.Validation.MissingValue("data", Option.Some(body.message)),
                                )
                            )
                    }
                    else -> {
                        DivaResult.failure(
                            DivaError(
                                cause = ErrorCause.Network.Error(
                                    method = HttpRequestMethod.GET,
                                    url = "/api/interaction/$id",
                                    status = response.status.toHttpStatusCodes(),
                                    details = Option.Some(body.message)
                                )
                            )
                        )
                    }
                }
            }
        }
    }

    override suspend fun create(
        dto: CreateInteractionDto,
        token: String
    ): DivaResult<InteractionResponse, DivaError> {
        return tryResult(
            onError = { e -> e.toDivaError() }
        ) {
            client.post(
                path = "/api/interaction",
                body = dto,
                headers = mapOf("Authorization" to "Bearer $token")
            ).flatMap { response ->
                val body: ApiResponse<InteractionResponse> = response.body()
                when (response.status) {
                    HttpStatusCode.Created -> {
                        body.data?.let { data -> DivaResult.success(data) }
                            ?: DivaResult.failure(
                                DivaError(
                                    cause = ErrorCause.Validation.MissingValue("data", Option.Some(body.message)),
                                )
                            )
                    }
                    else -> {
                        DivaResult.failure(
                            DivaError(
                                cause = ErrorCause.Network.Error(
                                    method = HttpRequestMethod.POST,
                                    url = "/api/interaction",
                                    status = response.status.toHttpStatusCodes(),
                                    details = Option.Some(body.message)
                                )
                            )
                        )
                    }
                }
            }
        }
    }

    override suspend fun update(
        id: String,
        dto: UpdateInteractionDto,
        token: String
    ): DivaResult<InteractionResponse, DivaError> {
        return tryResult(
            onError = { e -> e.toDivaError() }
        ) {
            client.put(
                path = "/api/interaction/$id",
                body = dto,
                headers = mapOf("Authorization" to "Bearer $token")
            ).flatMap { response ->
                val body: ApiResponse<InteractionResponse> = response.body()
                when (response.status) {
                    HttpStatusCode.Accepted -> {
                        body.data?.let { data -> DivaResult.success(data) }
                            ?: DivaResult.failure(
                                DivaError(
                                    cause = ErrorCause.Validation.MissingValue("data", Option.Some(body.message)),
                                )
                            )
                    }
                    else -> {
                        DivaResult.failure(
                            DivaError(
                                cause = ErrorCause.Network.Error(
                                    method = HttpRequestMethod.PUT,
                                    url = "/api/interaction/$id",
                                    status = response.status.toHttpStatusCodes(),
                                    details = Option.Some(body.message)
                                )
                            )
                        )
                    }
                }
            }
        }
    }

    override suspend fun delete(id: String, token: String): DivaResult<Unit, DivaError> {
        return tryResult(
            onError = { e -> e.toDivaError() }
        ) {
            client.delete(
                path = "/api/interaction/$id",
                headers = mapOf("Authorization" to "Bearer $token")
            ).flatMap { response ->
                val body: ApiResponse<Nothing> = response.body()
                when (response.status) {
                    HttpStatusCode.NoContent -> DivaResult.success(Unit)
                    else -> {
                        DivaResult.failure(
                            DivaError(
                                cause = ErrorCause.Network.Error(
                                    method = HttpRequestMethod.DELETE,
                                    url = "/api/interaction/$id",
                                    status = response.status.toHttpStatusCodes(),
                                    details = Option.Some(body.message)
                                )
                            )
                        )
                    }
                }
            }
        }
    }
}
