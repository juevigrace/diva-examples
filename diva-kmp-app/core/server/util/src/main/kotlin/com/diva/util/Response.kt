package com.diva.util

import com.diva.models.api.ApiResponse
import io.github.juevigrace.diva.core.DivaResult
import io.github.juevigrace.diva.core.errors.DivaError
import io.github.juevigrace.diva.core.errors.ErrorCause
import io.github.juevigrace.diva.core.fold
import io.github.juevigrace.diva.core.network.HttpStatusCodes
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.response.respond

suspend inline fun <reified T : Any> ApplicationCall.sendResponse(res: ApiResponse<T>) {
    respond(HttpStatusCode.fromValue(res.statusCode), res)
}

suspend inline fun <reified T : Any> DivaResult<ApiResponse<T>, DivaError>.respond(call: ApplicationCall) {
    fold(
        onSuccess = { res -> call.sendResponse(res) },
        onFailure = { err ->
            call.sendResponse(
                ApiResponse(
                    statusCode = when (val cause: ErrorCause = err.cause) {
                        is ErrorCause.Database.Duplicated -> {
                            HttpStatusCodes.Conflict.code
                        }
                        is ErrorCause.Error.Ex -> {
                            HttpStatusCodes.InternalServerError.code
                        }
                        is ErrorCause.Error.NotImplemented -> {
                            HttpStatusCodes.NotImplemented.code
                        }
                        is ErrorCause.Validation.MissingValue, is ErrorCause.Database.NoRowsAffected -> {
                            HttpStatusCodes.NotFound.code
                        }
                        is ErrorCause.Validation.UnexpectedValue,
                        is ErrorCause.Validation.Parse,
                        is ErrorCause.Validation.Expired,
                        is ErrorCause.Validation.Used -> {
                            HttpStatusCodes.BadRequest.code
                        }
                        is ErrorCause.Network -> cause.status.code
                    },
                    data = null,
                    message = err.message
                )
            )
        }
    )
}

suspend inline fun ApplicationCall.respondUnauthorized() {
    sendResponse(ApiResponse(statusCode = HttpStatusCodes.Unauthorized.code, message = "authenticate first"))
}
