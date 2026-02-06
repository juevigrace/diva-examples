package com.diva.collection.api.routes

import com.diva.models.api.ApiResponse
import com.diva.models.api.collection.dtos.CreateCollectionDto
import com.diva.models.api.collection.dtos.UpdateCollectionDto
import com.diva.models.server.AUTH_JWT_KEY
import com.diva.collection.api.handler.CollectionHandler
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

fun Route.collectionApiRoutes() {
    val handler: CollectionHandler by inject()
    route("/collection") {
        get {
            val page: Int = call.queryParameters["page"]?.toIntOrNull() ?: 1
            val pageSize: Int = call.queryParameters["pageSize"]?.toIntOrNull() ?: 10
            handler.getCollections(page, pageSize).respond(call)
        }
        route("/{id}") {
            get {
                val id: String = call.pathParameters["id"] ?: return@get call.respond(
                    HttpStatusCode.BadRequest,
                    ApiResponse(data = null, message = "Missing id")
                )
                handler.getCollection(id).respond(call)
            }
            authenticate(AUTH_JWT_KEY) {
                put {
                    val id: String = call.pathParameters["id"] ?: return@put call.respond(
                        HttpStatusCode.BadRequest,
                        ApiResponse(data = null, message = "Missing id")
                    )
                    val dto: UpdateCollectionDto = call.receive()
                    handler.updateCollection(id, dto).respond(call)
                }
                delete {
                    val id: String = call.pathParameters["id"] ?: return@delete call.respond(
                        HttpStatusCode.BadRequest,
                        ApiResponse(data = null, message = "Missing id")
                    )
                    handler.deleteCollection(id).respond(call)
                }
            }
        }
        authenticate(AUTH_JWT_KEY) {
            post {
                val dto: CreateCollectionDto = call.receive()
                handler.createCollection(dto).respond(call)
            }
        }
    }
}