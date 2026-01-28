package com.diva.media.api.handler

import com.diva.models.server.AUTH_JWT_KEY
import io.ktor.server.auth.authenticate
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.put
import io.ktor.server.routing.route

fun Route.tagApiHandler() {
    route("/tag") {
        get {
        }
        get("/{id}") {
        }
        authenticate(AUTH_JWT_KEY) {
            post {
            }
            put {
            }
            delete {
            }
        }
    }
}
