package com.diva.server.router

import com.diva.auth.api.handler.authApiHandler
import com.diva.chat.api.handler.chatApiHandler
import com.diva.collection.api.handler.collectionApiHandler
import com.diva.database.DivaDB
import com.diva.media.api.handler.mediaApiHandler
import com.diva.media.api.handler.tagApiHandler
import com.diva.models.api.ApiResponse
import com.diva.permissions.api.handler.permissionsApiHandler
import com.diva.social.api.handler.socialApiHandler
import com.diva.user.api.handler.userApiHandler
import io.github.juevigrace.diva.core.fold
import io.github.juevigrace.diva.database.DivaDatabase
import io.ktor.http.HttpStatusCode
import io.ktor.server.response.respond
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get
import io.ktor.server.routing.route
import io.ktor.server.sse.sse
import io.ktor.server.websocket.webSocket
import io.ktor.sse.ServerSentEvent
import io.ktor.websocket.CloseReason
import io.ktor.websocket.Frame
import io.ktor.websocket.close
import io.ktor.websocket.readText
import org.koin.ktor.ext.inject

fun Routing.applicationRoutes() {
    val db: DivaDatabase<DivaDB> by inject()
    route("/") {
        sse("/see/hello") {
            send(ServerSentEvent("world"))
        }
        webSocket("/ws") { // websocketSession
            for (frame in incoming) {
                if (frame is Frame.Text) {
                    val text = frame.readText()
                    outgoing.send(Frame.Text("YOU SAID: $text"))
                    if (text.equals("bye", ignoreCase = true)) {
                        close(CloseReason(CloseReason.Codes.NORMAL, "Client said BYE"))
                    }
                }
            }
        }
        get("/health") {
            db.checkHealth().fold(
                onFailure = {
                    call.respond(
                        HttpStatusCode.InternalServerError,
                        ApiResponse<Nothing>(message = it.message)
                    )
                },
                onSuccess = { call.respond(HttpStatusCode.OK, ApiResponse(data = it, message = "OK")) }
            )
        }
        route("/api") {
            authApiHandler()
            chatApiHandler()
            collectionApiHandler()
            mediaApiHandler()
            permissionsApiHandler()
            socialApiHandler()
            tagApiHandler()
            userApiHandler()
        }
    }
}
