package com.diva.social.api.handler

import com.diva.models.server.AUTH_JWT_KEY
import io.ktor.server.auth.authenticate
import io.ktor.server.routing.Routing
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route

internal fun Routing.interactionHandler() {
    route("/interaction") {
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
        route("/comment") {
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
        route("/share") {
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
}
