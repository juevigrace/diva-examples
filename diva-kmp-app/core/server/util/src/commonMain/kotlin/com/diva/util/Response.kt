package com.diva.util

import com.diva.models.api.ApiResponse
import io.github.juevigrace.diva.core.models.DivaError
import io.github.juevigrace.diva.core.models.DivaResult
import io.ktor.http.HttpStatusCode
import io.ktor.server.response.respond
import io.ktor.server.routing.RoutingCall

suspend fun DivaResult<ApiResponse<*>, DivaError>.respond(call: RoutingCall) {
    when (this) {
        is DivaResult.Failure -> {
            when (err) {
                is DivaError.NetworkError -> {
                    call.respond(
                        HttpStatusCode.fromValue(
                            (err as DivaError.NetworkError).statusCode
                        ),
                        ApiResponse<Nothing>(message = err.message)
                    )
                }
                else -> {
                    call.respond(HttpStatusCode.InternalServerError, ApiResponse<Nothing>(message = err.message))
                }
            }
        }
        is DivaResult.Success -> {
            call.respond(HttpStatusCode.fromValue(value.statusCode), value)
        }
    }
}
