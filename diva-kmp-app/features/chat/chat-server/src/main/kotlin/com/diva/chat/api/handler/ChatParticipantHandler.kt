package com.diva.chat.api.handler

import com.diva.chat.data.ChatParticipantService
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.put
import io.ktor.server.routing.route
import org.koin.ktor.ext.inject

internal fun Route.chatParticipantHandler() {
    val service: ChatParticipantService by inject()

    route("/participant") {
        get {
        }
        get("/{id}") {
        }
        // todo: protect this
        post {
        }
        put {
        }
        delete {
        }
    }
}
