package com.diva.chat.api.handler

import com.diva.models.server.AUTH_JWT_KEY
import io.ktor.server.auth.authenticate
import io.ktor.server.routing.Routing
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.put
import io.ktor.server.routing.route

fun Routing.chatApiHandler() {
    route("/chat") {
        authenticate(AUTH_JWT_KEY) {
            get("/") {
            }
            get("/{id}") {
            }
            post("/") {
            }
            put("/") {
            }
            delete("/") {
            }
            // todo: protect this
            chatParticipantHandler()
            chatMessageHandler()
        }
    }
}
