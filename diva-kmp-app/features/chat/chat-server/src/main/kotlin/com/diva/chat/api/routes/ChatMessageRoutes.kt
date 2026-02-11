package com.diva.chat.api.routes

import com.diva.chat.api.handler.ChatMessageHandler
import com.diva.models.api.ApiResponse
import com.diva.models.api.chat.dtos.CreateMessageDto
import com.diva.models.api.chat.dtos.DeleteMessageDto
import com.diva.models.api.chat.dtos.UpdateMessageDto
import com.diva.util.respond
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.put
import io.ktor.server.routing.route
import org.koin.ktor.ext.inject

internal fun Route.chatMessageApiRoutes() {
    val handler: ChatMessageHandler by inject()
    route("/message") {
        get("/{chatId}") {
            val chatId: String = call.pathParameters["chatId"]
                ?: return@get call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = ApiResponse(data = null, message = "Missing chatId")
                )
            val page: Int = call.queryParameters["page"]?.toIntOrNull() ?: 1
            val pageSize: Int = call.queryParameters["pageSize"]?.toIntOrNull() ?: 10
            handler.getChatMessages(chatId, page, pageSize).respond(call)
        }
        post {
            val dto: CreateMessageDto = call.receive()
            handler.createChatMessage(dto).respond(call)
        }
        put {
            val dto: UpdateMessageDto = call.receive()
            handler.updateChatMessage(dto).respond(call)
        }
        delete {
            val dto: DeleteMessageDto = call.receive()
            handler.deleteChatMessage(dto).respond(call)
        }
    }
}
