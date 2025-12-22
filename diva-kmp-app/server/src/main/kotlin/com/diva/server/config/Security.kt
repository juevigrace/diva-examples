package com.diva.server.config

import com.diva.models.ApiResponse
import com.diva.util.JwtHelper
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.auth.authentication
import io.ktor.server.auth.jwt.jwt
import io.ktor.server.response.respond
import org.koin.core.parameter.parametersOf
import org.koin.ktor.ext.inject

fun Application.configureSecurity() {
    // Please read the jwt property from the config file if you are using EngineMain
    val secret: String = environment.config.property("jwt.secret").getString()
    val issuer: String = environment.config.property("jwt.issuer").getString()
    val audience: String = environment.config.property("jwt.audience").getString()
    val jwtRealm: String = environment.config.property("jwt.realm").getString()
    val jwtHelper: JwtHelper by inject { parametersOf(secret, issuer, audience) }

    authentication {
        jwt("auth-jwt") {
            realm = jwtRealm
            verifier(jwtHelper.jwtVerifier)
            validate { credential ->
                jwtHelper.validate(credential) { _ ->
                    TODO()
                }
            }
            challenge { _, _ ->
                call.respond(HttpStatusCode.Unauthorized, ApiResponse<Nothing>(message = "Invalid token"))
            }
        }
    }
}
