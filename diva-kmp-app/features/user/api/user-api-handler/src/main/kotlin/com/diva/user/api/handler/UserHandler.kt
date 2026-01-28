package com.diva.user.api.handler

import com.diva.mail.KMail
import com.diva.mail.buildCodeVerificationEmail
import com.diva.models.api.ApiResponse
import com.diva.models.api.user.dtos.CreateUserDto
import com.diva.models.api.user.dtos.UpdateUserDto
import com.diva.models.api.user.dtos.UserEmailDto
import com.diva.models.auth.Session
import com.diva.models.server.AUTH_JWT_KEY
import com.diva.models.server.SESSION_KEY
import com.diva.user.data.UserService
import com.diva.util.Encryption
import com.diva.util.respond
import com.diva.verification.data.VerificationService
import io.github.juevigrace.diva.core.errors.asNetworkError
import io.github.juevigrace.diva.core.map
import io.github.juevigrace.diva.core.mapError
import io.github.juevigrace.diva.core.network.HttpRequestMethod
import io.github.juevigrace.diva.core.network.HttpStatusCodes
import io.ktor.http.HttpStatusCode
import io.ktor.server.auth.authenticate
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.patch
import io.ktor.server.routing.post
import io.ktor.server.routing.put
import io.ktor.server.routing.route
import org.koin.ktor.ext.inject
import kotlin.uuid.ExperimentalUuidApi

// TODO: rethink admin privileges and available actions
@OptIn(ExperimentalUuidApi::class)
fun Route.userApiHandler() {
    val service: UserService by inject()
    val verificationService: VerificationService by inject()
    val kMail: KMail by inject()

    route("/user") {
        get {
            val page: Int = call.queryParameters["page"]?.toIntOrNull() ?: 1
            val pageSize: Int = call.queryParameters["pageSize"]?.toIntOrNull() ?: 10
            service.getUsers(page, pageSize).respond(call)
        }

        route("/{id}") {
            get {
                val idStr: String = call.pathParameters["id"] ?: return@get call.respond(
                    HttpStatusCode.BadRequest,
                    ApiResponse<Nothing>(message = "Missing id")
                )
                service.getUser(idStr).respond(call)
            }
            // TODO: block with admin role or explicit permission
            authenticate(AUTH_JWT_KEY) {
                delete {
                    val idStr: String = call.pathParameters["id"] ?: return@delete call.respond(
                        HttpStatusCode.BadRequest,
                        ApiResponse<Nothing>(message = "Missing id")
                    )
                    service.deleteUser(idStr).respond(call)
                }
            }
            userPermissionsHandler()
        }

        authenticate(AUTH_JWT_KEY) {
            // TODO: block with admin role or explicit permission
            post {
                val dto: CreateUserDto = call.receive()
                service
                    .createUser(dto.copy(password = Encryption.hashPassword(dto.password))) { id ->
                        verificationService.createVerificationCode(id).map { v ->
                            kMail.sendEmail(
                                to = dto.email,
                                subject = "Email Verification",
                                html = buildCodeVerificationEmail(v)
                            )
                        }
                    }
                    .map { id -> ApiResponse(data = id.toString(), message = "User created") }
                    .mapError { err ->
                        err.asNetworkError(
                            method = HttpRequestMethod.POST,
                            url = "/api/user",
                            statusCode = HttpStatusCodes.InternalServerError,
                        )
                    }
                    .respond(call)
            }

            // TODO: block with admin role or explicit permission
            put("/{id}") {
                val idStr: String = call.pathParameters["id"] ?: return@put call.respond(
                    HttpStatusCode.BadRequest,
                    ApiResponse<Nothing>(message = "Missing id")
                )

                val dto: UpdateUserDto = call.receive()
                service.updateUser(dto, idStr).respond(call)
            }

            route("/me") {
                put {
                    val session: Session = call.attributes.getOrNull(SESSION_KEY)
                        ?: return@put call.respond(
                            status = HttpStatusCode.Unauthorized,
                            message = ApiResponse<Nothing>(message = "You are not authenticated")
                        )
                    val dto: UpdateUserDto = call.receive()
                    service.updateUser(dto, session.id.toString()).respond(call)
                }

                route("/update") {
                    route("/email") {
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

                        patch {
                            val session: Session = call.attributes.getOrNull(SESSION_KEY)
                                ?: return@patch call.respond(
                                    status = HttpStatusCode.Unauthorized,
                                    message = ApiResponse<Nothing>(message = "You are not authenticated")
                                )
                            val dto: UserEmailDto = call.receive()
                            service.updateEmail(dto, session).respond(call)
                        }
                    }
                }
                delete {
                    val session: Session = call.attributes.getOrNull(SESSION_KEY)
                        ?: return@delete call.respond(
                            status = HttpStatusCode.Unauthorized,
                            message = ApiResponse<Nothing>(message = "You are not authenticated")
                        )
                    service.deleteUser(session.id.toString()).respond(call)
                }
            }
        }
    }
}
