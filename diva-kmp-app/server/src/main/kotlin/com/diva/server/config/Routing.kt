package com.diva.server.config

import com.diva.models.api.ApiResponse
import com.diva.server.router.applicationRoutes
import com.diva.server.validation.appRequestValidation
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.http.content.staticResources
import io.ktor.server.plugins.requestvalidation.RequestValidation
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.response.respond
import io.ktor.server.routing.routing
import io.ktor.server.sse.SSE
import io.ktor.server.websocket.WebSockets
import io.ktor.server.websocket.pingPeriod
import io.ktor.server.websocket.timeout
import kotlin.time.Duration.Companion.seconds

fun Application.configureRouting() {
    install(StatusPages) {
        exception<Exception> { call, cause ->
            call.respond(HttpStatusCode.InternalServerError, ApiResponse<Nothing>(message = "error: ${cause.message}"))
        }
    }
    install(SSE)
    install(WebSockets) {
        pingPeriod = 15.seconds
        timeout = 15.seconds
        maxFrameSize = Long.MAX_VALUE
        masking = false
    }
    install(RequestValidation) {
        appRequestValidation()
    }
    routing {
        applicationRoutes()
        staticResources("/", "static")
    }
}
