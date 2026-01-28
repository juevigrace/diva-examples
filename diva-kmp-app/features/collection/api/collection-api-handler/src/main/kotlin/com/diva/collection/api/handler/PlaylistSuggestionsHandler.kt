package com.diva.collection.api.handler

import com.diva.collection.data.playlist.PlaylistSuggestionService
import com.diva.models.server.AUTH_JWT_KEY
import io.ktor.server.auth.authenticate
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.put
import io.ktor.server.routing.route
import org.koin.ktor.ext.inject

internal fun Route.playlistSuggestionsHandler() {
    val service: PlaylistSuggestionService by inject()

    route("/suggestions") {
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
