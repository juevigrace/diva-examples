package com.diva.util

import com.diva.models.api.ApiResponse
import com.diva.models.api.action.ActionResponse
import io.github.juevigrace.diva.core.DivaResult
import io.github.juevigrace.diva.core.errors.DivaError
import io.github.juevigrace.diva.core.errors.ErrorCause
import io.github.juevigrace.diva.core.errors.toHttpStatusCodes
import io.github.juevigrace.diva.core.fold
import io.github.juevigrace.diva.core.network.HttpStatusCodes
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.response.respond

suspend inline fun<reified T> ApplicationCall.sendResponse(res: ApiResponse<T>) {
    respond(HttpStatusCode.fromValue(res.statusCode), res)
}

suspend inline fun <reified T> DivaResult<ApiResponse<T>, DivaError>.respond(call: ApplicationCall) {
    fold(
        onSuccess = { res -> call.sendResponse(res) },
        onFailure = { err ->
            call.sendResponse(
                ApiResponse<T>(
                    statusCode = err.cause.toHttpStatusCodes().code,
                    message = err.message
                )
            )
        }
    )
}

suspend inline fun <reified T, reified R> DivaResult<ApiResponse<T>, DivaError>.respond(
    call: ApplicationCall,
    transform: (ErrorCause) -> R = { Unit as R }
) {
    fold(
        onSuccess = { res -> call.sendResponse(res) },
        onFailure = { err ->
            call.sendResponse<R>(
                ApiResponse(
                    statusCode = err.cause.toHttpStatusCodes().code,
                    data = transform(err.cause),
                    message = err.message
                )
            )
        }
    )
}

suspend fun ApplicationCall.respondBadRequest(message: String) {
    sendResponse(
        ApiResponse<Unit>(
            statusCode = HttpStatusCodes.BadRequest.code,
            message = message
        )
    )
}

suspend inline fun ApplicationCall.respondUnauthorized() {
    sendResponse(
        ApiResponse<Unit>(
            statusCode = HttpStatusCodes.Unauthorized.code,
            message = "authenticate first"
        )
    )
}

suspend inline fun ApplicationCall.respondNotFound(message: String) {
    sendResponse(
        ApiResponse<Unit>(
            statusCode = HttpStatusCodes.NotFound.code,
            message = message
        )
    )
}

suspend inline fun ApplicationCall.respondInternalServerError(message: String) {
    sendResponse(
        ApiResponse<Unit>(
            statusCode = HttpStatusCodes.InternalServerError.code,
            message = message
        )
    )
}
