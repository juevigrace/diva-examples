package com.diva.collection.api.routes

import com.diva.collection.api.handler.PlaylistContributorHandler
import com.diva.models.api.ApiResponse
import com.diva.models.api.collection.dtos.PlaylistContributorDto
import com.diva.models.server.AUTH_JWT_KEY
import com.diva.util.respond
import io.ktor.http.HttpStatusCode
import io.ktor.server.auth.authenticate
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import org.koin.ktor.ext.inject

internal fun Route.playlistContributorApiRoutes() {
    val handler: PlaylistContributorHandler by inject()
    route("/contributor") {
        get("/{playlistId}") {
            val playlistId: String = call.pathParameters["playlistId"] ?: return@get call.respond(
                HttpStatusCode.BadRequest,
                ApiResponse(data = null, message = "Missing playlistId")
            )
            handler.getContributors(playlistId).respond(call)
        }
        authenticate(AUTH_JWT_KEY) {
            post {
                val dto: PlaylistContributorDto = call.receive()
                handler.createPlaylistContributor(dto).respond(call)
            }
            delete("/{id}") {
                val dto: PlaylistContributorDto = call.receive()
                handler.deletePlaylistContributor(dto).respond(call)
            }
        }
    }
}
