package com.diva.social.api.routes

import com.diva.models.api.ApiResponse
import com.diva.models.api.social.interaction.dtos.CreateInteractionDto
import com.diva.models.api.social.interaction.dtos.UpdateInteractionDto
import com.diva.models.server.AUTH_JWT_KEY
import com.diva.social.api.handler.InteractionHandler
import com.diva.util.respond
import io.ktor.http.HttpStatusCode
import io.ktor.server.auth.authenticate
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.put
import io.ktor.server.routing.route
import org.koin.ktor.ext.inject

fun Route.interactionApiRoutes() {
    val handler: InteractionHandler by inject()
    route("/interaction") {
        get {
            val page: Int = call.queryParameters["page"]?.toIntOrNull() ?: 1
            val pageSize: Int = call.queryParameters["pageSize"]?.toIntOrNull() ?: 10
            handler.getInteractions(page, pageSize).respond(call)
        }
        route("/{id}") {
            get {
                val id: String = call.pathParameters["id"] ?: return@get call.respond(
                    HttpStatusCode.BadRequest,
                    ApiResponse(data = null, message = "Missing id")
                )
                handler.getInteraction(id).respond(call)
            }
            authenticate(AUTH_JWT_KEY) {
                put {
                    val id: String = call.pathParameters["id"] ?: return@put call.respond(
                        HttpStatusCode.BadRequest,
                        ApiResponse(data = null, message = "Missing id")
                    )
                    val dto: UpdateInteractionDto = call.receive()
                    handler.updateInteraction(id, dto).respond(call)
                }
                delete {
                    val id: String = call.pathParameters["id"] ?: return@delete call.respond(
                        HttpStatusCode.BadRequest,
                        ApiResponse(data = null, message = "Missing id")
                    )
                    handler.deleteInteraction(id).respond(call)
                }
            }
        }
        authenticate(AUTH_JWT_KEY) {
            post {
                val dto: CreateInteractionDto = call.receive()
                handler.createInteraction(dto).respond(call)
            }
        }
    }
}