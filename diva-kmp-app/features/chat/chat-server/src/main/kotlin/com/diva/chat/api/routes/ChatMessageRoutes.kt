package com.diva.chat.api.routes

import com.diva.models.api.ApiResponse
import com.diva.models.api.chat.message.dtos.CreateChatMessageDto
import com.diva.models.api.chat.message.dtos.UpdateChatMessageDto
import com.diva.models.server.AUTH_JWT_KEY
import com.diva.chat.api.handler.ChatMessageHandler
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

fun Route.chatMessageApiRoutes() {
    val handler: ChatMessageHandler by inject()
    route("/chat/message") {
        authenticate(AUTH_JWT_KEY) {
            get {
                val page: Int = call.queryParameters["page"]?.toIntOrNull() ?: 1
                val pageSize: Int = call.queryParameters["pageSize"]?.toIntOrNull() ?: 10
                handler.getChatMessages(page, pageSize).respond(call)
            }
            route("/{id}") {
                get {
                    val id: String = call.pathParameters["id"] ?: return@get call.respond(
                        HttpStatusCode.BadRequest,
                        ApiResponse(data = null, message = "Missing id")
                    )
                    handler.getChatMessage(id).respond(call)
                }
                put {
                    val id: String = call.pathParameters["id"] ?: return@put call.respond(
                        HttpStatusCode.BadRequest,
                        ApiResponse(data = null, message = "Missing id")
                    )
                    val dto: UpdateChatMessageDto = call.receive()
                    handler.updateChatMessage(id, dto).respond(call)
                }
                delete {
                    val id: String = call.pathParameters["id"] ?: return@delete call.respond(
                        HttpStatusCode.BadRequest,
                        ApiResponse(data = null, message = "Missing id")
                    )
                    handler.deleteChatMessage(id).respond(call)
                }
            }
            post {
                val dto: CreateChatMessageDto = call.receive()
                handler.createChatMessage(dto).respond(call)
            }
        }
    }
}