package com.diva.util

import com.diva.models.api.ApiResponse
import io.github.juevigrace.diva.core.models.DivaError
import io.github.juevigrace.diva.core.models.DivaResult
import io.ktor.http.HttpStatusCode
import io.ktor.server.response.respond
import io.ktor.server.routing.RoutingCall

suspend fun DivaResult<ApiResponse<*>, DivaError.NetworkError>.respond(call: RoutingCall) {
    when (this) {
        is DivaResult.Failure -> {
            call.respond(HttpStatusCode.fromValue(err.statusCode), err.message)
        }
        is DivaResult.Success -> {
            call.respond(HttpStatusCode.fromValue(value.statusCode), value)
        }
    }
}
