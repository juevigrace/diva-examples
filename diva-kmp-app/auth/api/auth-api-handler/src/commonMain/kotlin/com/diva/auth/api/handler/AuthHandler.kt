package com.diva.auth.api.handler

import com.diva.auth.data.service.AuthService
import com.diva.models.auth.dtos.SignInDto
import io.github.juevigrace.diva.core.models.DivaResult
import io.ktor.http.HttpStatusCode
import io.ktor.server.auth.authenticate
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.Routing
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import org.koin.ktor.ext.inject

fun Routing.authRoutes(apiGroup: (route: Route.() -> Unit) -> Unit) {
    val service: AuthService by inject()

    apiGroup {
        route("/auth") {
            post("/signIn") {
                val dto: SignInDto = call.receive()

                when (val result = service.signIn(dto)) {
                    is DivaResult.Failure -> {
                        call.respond(HttpStatusCode.fromValue(result.err.statusCode!!), result.err.message)
                    }
                    is DivaResult.Success -> {
                        call.respond(HttpStatusCode.OK, result.value)
                    }
                }
            }

            authenticate("auth-jwt") {
                post("/refresh") {
                    call.attributes
                }
            }
        }
    }

    route("/auth") {}
}
