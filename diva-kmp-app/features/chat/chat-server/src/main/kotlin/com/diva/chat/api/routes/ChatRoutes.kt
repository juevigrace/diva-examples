package com.diva.chat.api.routes

import com.diva.models.api.ApiResponse
import com.diva.models.api.chat.dtos.CreateChatDto
import com.diva.models.api.chat.dtos.UpdateChatDto
import com.diva.models.server.AUTH_JWT_KEY
import com.diva.chat.api.handler.ChatHandler
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

fun Route.chatApiRoutes() {
    val handler: ChatHandler by inject()
    route("/chat") {
        authenticate(AUTH_JWT_KEY) {
            get {
                val page: Int = call.queryParameters["page"]?.toIntOrNull() ?: 1
                val pageSize: Int = call.queryParameters["pageSize"]?.toIntOrNull() ?: 10
                handler.getChats(page, pageSize).respond(call)
            }
            route("/{id}") {
                get {
                    val id: String = call.pathParameters["id"] ?: return@get call.respond(
                        HttpStatusCode.BadRequest,
                        ApiResponse(data = null, message = "Missing id")
                    )
                    handler.getChat(id).respond(call)
                }
                put {
                    val id: String = call.pathParameters["id"] ?: return@put call.respond(
                        HttpStatusCode.BadRequest,
                        ApiResponse(data = null, message = "Missing id")
                    )
                    val dto: UpdateChatDto = call.receive()
                    handler.updateChat(id, dto).respond(call)
                }
                delete {
                    val id: String = call.pathParameters["id"] ?: return@delete call.respond(
                        HttpStatusCode.BadRequest,
                        ApiResponse(data = null, message = "Missing id")
                    )
                    handler.deleteChat(id).respond(call)
                }
            }
            post {
                val dto: CreateChatDto = call.receive()
                handler.createChat(dto).respond(call)
            }
        }
    }
}