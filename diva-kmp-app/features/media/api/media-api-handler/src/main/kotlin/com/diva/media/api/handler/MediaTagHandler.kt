package com.diva.media.api.handler

import com.diva.models.server.AUTH_JWT_KEY
import io.ktor.server.auth.authenticate
import io.ktor.server.routing.Routing
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route

internal fun Routing.mediaTagHandler() {
    route("/tag") {
        get("/") {
        }
        get("/{id}") {
        }
        authenticate(AUTH_JWT_KEY) {
            post("/") {
            }
            delete("/") {
            }
        }
    }
}
