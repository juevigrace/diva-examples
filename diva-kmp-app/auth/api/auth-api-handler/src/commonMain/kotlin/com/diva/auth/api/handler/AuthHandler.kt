package com.diva.auth.api.handler

import com.diva.auth.data.AuthService
import com.diva.models.api.ApiResponse
import com.diva.models.api.auth.dtos.PasswordResetConfirmDto
import com.diva.models.api.auth.dtos.PasswordResetRequestDto
import com.diva.models.api.auth.dtos.SessionDataDto
import com.diva.models.api.auth.dtos.SignInDto
import com.diva.models.api.user.dtos.CreateUserDto
import com.diva.models.auth.Session
import com.diva.models.server.AUTH_JWT_KEY
import com.diva.models.server.SESSION_KEY
import com.diva.user.data.UserService
import com.diva.util.respond
import io.ktor.http.HttpStatusCode
import io.ktor.server.auth.authenticate
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Routing
import io.ktor.server.routing.patch
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import org.koin.ktor.ext.inject
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalUuidApi::class)
fun Routing.authApiHandler() {
    val service: AuthService by inject()
    val userService: UserService by inject()

    route("/auth") {
        post("/signIn") {
            val dto: SignInDto = call.receive()
            service.signIn(dto).respond(call)
        }

        post("/signUp") {
            val dto: CreateUserDto = call.receive()
            service.signUp(dto, userService::createUser).respond(call)
        }

        authenticate(AUTH_JWT_KEY) {
            post("/signOut") {
                val session: Session = call.attributes.getOrNull(SESSION_KEY)
                    ?: return@post call.respond(
                        status = HttpStatusCode.Unauthorized,
                        message = ApiResponse<Nothing>(message = "You are not authenticated")
                    )
                service.signOut(session.id).respond(call)
            }

            post("/ping") {
                val session: Session = call.attributes.getOrNull(SESSION_KEY)
                    ?: return@post call.respond(
                        status = HttpStatusCode.Unauthorized,
                        message = ApiResponse<Nothing>(message = "You are not authenticated")
                    )
                service.ping(session.id).respond(call)
            }

            post("/refresh") {
                val session: Session = call.attributes.getOrNull(SESSION_KEY)
                    ?: return@post call.respond(
                        status = HttpStatusCode.Unauthorized,
                        message = ApiResponse<Nothing>(message = "You are not authenticated")
                    )
                val dto: SessionDataDto = call.receive()
                service.refresh(dto, session).respond(call)
            }
        }

        route("/password/reset") {
            post("/request") {
                val dto: PasswordResetRequestDto = call.receive()
                service.passwordResetRequest(dto).respond(call)
            }

            authenticate(AUTH_JWT_KEY) {
                post("/confirm") {
                    val dto: PasswordResetConfirmDto = call.receive()
                    service.passwordResetConfirm(dto).respond(call)
                }

                patch("/") {
                    val dto: PasswordResetRequestDto = call.receive()
                    service.passwordReset(dto, userService::updatePassword).respond(call)
                }
            }
        }
    }
}
