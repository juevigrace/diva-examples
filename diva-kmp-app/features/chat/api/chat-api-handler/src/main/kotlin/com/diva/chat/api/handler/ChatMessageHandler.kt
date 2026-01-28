package com.diva.chat.api.handler

import com.diva.chat.data.ChatMessageService
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.put
import io.ktor.server.routing.route
import org.koin.ktor.ext.inject

internal fun Route.chatMessageHandler() {
    val service: ChatMessageService by inject()

    route("/message") {
        // todo: ws
        get {
        }

        post {
        }
        put {
        }
        delete {
        }

        route("media") {
            post {
            }
            delete {
            }
        }
    }
}
