package com.diva.user.api.handler

import com.diva.models.server.AUTH_JWT_KEY
import com.diva.user.data.UserPermissionsService
import io.ktor.server.auth.authenticate
import io.ktor.server.routing.Routing
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.put
import io.ktor.server.routing.route
import org.koin.ktor.ext.inject

internal fun Routing.userPermissionsHandler() {
    val service: UserPermissionsService by inject()

    route("/permissions") {
        get("/") {
        }
        get("/{id}") {
        }
        authenticate(AUTH_JWT_KEY) {
            post("/") {
            }
            put("/") {
            }
            delete("/{id}") {
            }
        }
    }
}
