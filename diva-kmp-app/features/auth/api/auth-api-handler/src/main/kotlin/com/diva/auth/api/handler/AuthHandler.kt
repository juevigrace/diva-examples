package com.diva.auth.api.handler

import com.diva.auth.data.AuthService
import com.diva.mail.KMail
import com.diva.mail.buildCodeVerificationEmail
import com.diva.models.api.ApiResponse
import com.diva.models.api.auth.dtos.PasswordUpdateDto
import com.diva.models.api.auth.dtos.SessionDataDto
import com.diva.models.api.auth.dtos.SignInDto
import com.diva.models.api.auth.dtos.SignUpDto
import com.diva.models.api.user.dtos.EmailTokenDto
import com.diva.models.auth.Session
import com.diva.models.server.AUTH_JWT_KEY
import com.diva.models.server.SESSION_KEY
import com.diva.user.data.UserService
import com.diva.util.respond
import com.diva.verification.data.VerificationService
import io.github.juevigrace.diva.core.DivaResult
import io.github.juevigrace.diva.core.map
import io.github.juevigrace.diva.core.onFailure
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
    val verificationService: VerificationService by inject()
    val kMail: KMail by inject()

    route("/auth") {
        post("/signIn") {
            val dto: SignInDto = call.receive()
            service.signIn(dto, onUserSearch = userService::getUserByUsername).respond(call)
        }

        post("/signUp") {
            val dto: SignUpDto = call.receive()
            service.signUp(dto) { userDto ->
                userService.createUser(userDto) { id ->
                    verificationService.createVerificationCode(id).map { v ->
                        kMail.sendEmail(
                            to = userDto.email,
                            subject = "Email Verification",
                            html = buildCodeVerificationEmail(v)
                        )
                    }
                }
            }.respond(call)
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

            post("/verify/email") {
                val session: Session = call.attributes.getOrNull(SESSION_KEY)
                    ?: return@post call.respond(
                        status = HttpStatusCode.Unauthorized,
                        message = ApiResponse<Nothing>(message = "You are not authenticated")
                    )
                val dto: EmailTokenDto = call.receive()
                service.verifyUser(
                    onVerify = {
                        verificationService.verify(dto.token)
                    },
                    onVerified = {
                        verificationService.deleteToken(dto.token).onFailure { err ->
                            return@verifyUser DivaResult.failure(err)
                        }
                        userService.updateVerified(session.user.id)
                    }
                ).respond(call)
            }
        }

        route("/forgot") {
            route("/password") {
                post("/request") {
                    call.respond(
                        HttpStatusCode.NotImplemented,
                        ApiResponse<Nothing>(message = "Not implemented")
                    )
                }

                post("/confirm") {
                    call.respond(
                        HttpStatusCode.NotImplemented,
                        ApiResponse<Nothing>(message = "Not implemented")
                    )
                }

                authenticate(AUTH_JWT_KEY) {
                    patch("/") {
                        val session: Session = call.attributes.getOrNull(SESSION_KEY)
                            ?: return@patch call.respond(
                                status = HttpStatusCode.Unauthorized,
                                message = ApiResponse<Nothing>(message = "You are not authenticated")
                            )

                        val dto: PasswordUpdateDto = call.receive()
                        service.passwordReset(dto) { hash ->
                            userService.updatePassword(session.user.id, hash)
                        }.respond(call)
                    }
                }
            }
        }
    }
}
