package com.diva.chat.api.handler

import com.diva.chat.data.ChatService
import com.diva.models.server.AUTH_JWT_KEY
import io.ktor.server.auth.authenticate
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.put
import io.ktor.server.routing.route
import org.koin.ktor.ext.inject

fun Route.chatApiHandler() {
    val service: ChatService by inject()

    route("/chat") {
        authenticate(AUTH_JWT_KEY) {
            get {
            }
            get("/{id}") {
                chatParticipantHandler()
                chatMessageHandler()
            }
            post {
            }
            put {
            }
            delete {
            }
        }
    }
}
