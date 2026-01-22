package com.diva.server.config

import com.diva.database.session.SessionStorage
import com.diva.models.api.ApiResponse
import com.diva.models.server.AUTH_JWT_KEY
import com.diva.models.server.SESSION_KEY
import com.diva.util.JwtHelper
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.auth.authentication
import io.ktor.server.auth.jwt.jwt
import io.ktor.server.response.respond
import org.koin.core.parameter.parametersOf
import org.koin.ktor.ext.inject
import kotlin.getValue
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.toKotlinUuid

@OptIn(ExperimentalUuidApi::class)
fun Application.configureSecurity() {
    // Please read the jwt property from the config file if you are using EngineMain
    val secret: String = environment.config.property("jwt.secret").getString()
    val issuer: String = environment.config.property("jwt.issuer").getString()
    val audience: String = environment.config.property("jwt.audience").getString()
    val jwtRealm: String = environment.config.property("jwt.realm").getString()
    val jwtHelper: JwtHelper by inject { parametersOf(secret, issuer, audience) }
    val sessionStorage: SessionStorage by inject()

    authentication {
        jwt(AUTH_JWT_KEY) {
            realm = jwtRealm
            verifier(jwtHelper.jwtVerifier)
            validate { credential ->
                jwtHelper.validate(
                    credential = credential,
                    sessionCallBack = { id ->
                        sessionStorage.getById(id.toKotlinUuid())
                    },
                    onFound = { session ->
                        attributes[SESSION_KEY] = session
                    }
                )
            }
            challenge { _, _ ->
                call.respond(HttpStatusCode.Unauthorized, ApiResponse<Nothing>(message = "Invalid token"))
            }
        }
    }
}
