package com.diva.collection.api.handler

import com.diva.models.server.AUTH_JWT_KEY
import io.ktor.server.auth.authenticate
import io.ktor.server.routing.Routing
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.put
import io.ktor.server.routing.route

internal fun Routing.playlistHandler() {
    route("/playlist") {
        get("/") {
        }
        route("/{id}") {
            get("/") {
            }
            playlistContributorHandler()
            playlistSuggestionsHandler()
        }
        authenticate(AUTH_JWT_KEY) {
            post("/") {
            }
            put("/") {
            }
            delete("/") {
            }
        }
    }
}
