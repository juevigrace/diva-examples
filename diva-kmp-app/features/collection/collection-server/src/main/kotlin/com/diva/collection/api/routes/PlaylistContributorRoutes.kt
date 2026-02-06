package com.diva.collection.api.routes

import com.diva.models.api.ApiResponse
import com.diva.models.api.collection.playlist.contributor.dtos.CreatePlaylistContributorDto
import com.diva.models.api.collection.playlist.contributor.dtos.UpdatePlaylistContributorDto
import com.diva.models.server.AUTH_JWT_KEY
import com.diva.collection.api.handler.PlaylistContributorHandler
import com.diva.util.respond
import io.ktor.http.HttpStatusCode
import io.ktor.server.auth.authenticate
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.put
import io.ktor.server.routing.route
import org.koin.ktor.ext.inject

fun Route.playlistContributorApiRoutes() {
    val handler: PlaylistContributorHandler by inject()
    route("/collection/playlist/contributor") {
        get {
            val page: Int = call.queryParameters["page"]?.toIntOrNull() ?: 1
            val pageSize: Int = call.queryParameters["pageSize"]?.toIntOrNull() ?: 10
            handler.getPlaylistContributors(page, pageSize).respond(call)
        }
        route("/{id}") {
            get {
                val id: String = call.pathParameters["id"] ?: return@get call.respond(
                    HttpStatusCode.BadRequest,
                    ApiResponse(data = null, message = "Missing id")
                )
                handler.getPlaylistContributor(id).respond(call)
            }
            authenticate(AUTH_JWT_KEY) {
                put {
                    val id: String = call.pathParameters["id"] ?: return@put call.respond(
                        HttpStatusCode.BadRequest,
                        ApiResponse(data = null, message = "Missing id")
                    )
                    val dto: UpdatePlaylistContributorDto = call.receive()
                    handler.updatePlaylistContributor(id, dto).respond(call)
                }
                delete {
                    val id: String = call.pathParameters["id"] ?: return@delete call.respond(
                        HttpStatusCode.BadRequest,
                        ApiResponse(data = null, message = "Missing id")
                    )
                    handler.deletePlaylistContributor(id).respond(call)
                }
            }
        }
        authenticate(AUTH_JWT_KEY) {
            post {
                val dto: CreatePlaylistContributorDto = call.receive()
                handler.createPlaylistContributor(dto).respond(call)
            }
        }
    }
}