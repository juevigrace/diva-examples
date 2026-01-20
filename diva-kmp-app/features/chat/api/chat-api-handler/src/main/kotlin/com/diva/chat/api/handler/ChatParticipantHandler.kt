package com.diva.chat.api.handler

import io.ktor.server.routing.Routing
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.put
import io.ktor.server.routing.route

internal fun Routing.chatParticipantHandler() {
    route("/participant") {
        get("/") {
        }
        get("/{id}") {
        }
        // todo: protect this
        post("/") {
        }
        put("/") {
        }
        delete("/") {
        }
    }
}
