package com.diva.collection.api.handler

import com.diva.models.server.AUTH_JWT_KEY
import io.ktor.server.auth.authenticate
import io.ktor.server.routing.Routing
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.put
import io.ktor.server.routing.route

fun Routing.collectionApiHandler() {
    route("/collection") {
        get("/") {
        }
        route("/{id}") {
            get("/") {
            }
        }
        authenticate(AUTH_JWT_KEY) {
            post("/") {
            }
            put("/") {
            }
            delete("/") {
            }
        }
        playlistHandler()
    }
}
