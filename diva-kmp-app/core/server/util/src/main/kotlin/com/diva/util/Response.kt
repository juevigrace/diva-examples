package com.diva.util

import com.diva.models.api.ApiResponse
import io.github.juevigrace.diva.core.DivaResult
import io.github.juevigrace.diva.core.errors.DivaError
import io.github.juevigrace.diva.core.onFailure
import io.github.juevigrace.diva.core.onSuccess
import io.ktor.http.HttpStatusCode
import io.ktor.server.response.respond
import io.ktor.server.routing.RoutingCall

suspend fun DivaResult<ApiResponse<*>, DivaError.NetworkError>.respond(call: RoutingCall) {
    onFailure { err ->
        call.respond(
            HttpStatusCode.fromValue(err.statusCode.code),
            ApiResponse<Nothing>(message = err.message)
        )
    }
    onSuccess { value ->
        call.respond(HttpStatusCode.fromValue(value.statusCode), value)
    }
}
