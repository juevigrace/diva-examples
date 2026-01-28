package com.diva.media.api.handler

import com.diva.media.data.MediaService
import com.diva.models.server.AUTH_JWT_KEY
import io.ktor.server.auth.authenticate
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.patch
import io.ktor.server.routing.post
import io.ktor.server.routing.put
import io.ktor.server.routing.route
import org.koin.ktor.ext.inject

fun Route.mediaApiHandler() {
    val service: MediaService by inject()

    route("/media") {
        get {
        }
        route("/{id}") {
            get { }
            mediaTagHandler()
        }
        authenticate(AUTH_JWT_KEY) {
            post {
            }
            put {
            }
            patch {
            }
            delete {
            }
        }
    }
}
