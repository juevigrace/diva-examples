package com.diva.collection.api.handler

import com.diva.collection.data.CollectionService
import com.diva.models.server.AUTH_JWT_KEY
import io.ktor.server.auth.authenticate
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.put
import io.ktor.server.routing.route
import org.koin.ktor.ext.inject

fun Route.collectionApiHandler() {
    val service: CollectionService by inject()

    route("/collection") {
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
        playlistHandler()
    }
}
