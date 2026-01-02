package com.diva.server.config

import com.diva.models.api.ApiResponse
import com.diva.models.auth.Session
import com.diva.models.server.AUTH_JWT_KEY
import com.diva.models.server.SESSION_KEY
import com.diva.session.database.shared.SessionStorage
import com.diva.util.JwtHelper
import io.github.juevigrace.diva.core.models.DivaError
import io.github.juevigrace.diva.core.models.DivaResult
import io.github.juevigrace.diva.core.models.Option
import io.github.juevigrace.diva.core.models.isEmpty
import io.github.juevigrace.diva.core.models.onSuccess
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.auth.authentication
import io.ktor.server.auth.jwt.jwt
import io.ktor.server.response.respond
import org.koin.core.parameter.parametersOf
import org.koin.ktor.ext.inject
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
                jwtHelper.validate(credential) { id ->
                    val result: DivaResult<Option<Session>, DivaError> = sessionStorage.getById(id.toKotlinUuid())
                    result.onSuccess { option ->
                        if (option.isEmpty()) {
                            return@onSuccess
                        }
                        attributes[SESSION_KEY] = (option as Option.Some).value
                    }
                    result
                }
            }
            challenge { _, _ ->
                call.respond(HttpStatusCode.Unauthorized, ApiResponse<Nothing>(message = "Invalid token"))
            }
        }
    }
}
