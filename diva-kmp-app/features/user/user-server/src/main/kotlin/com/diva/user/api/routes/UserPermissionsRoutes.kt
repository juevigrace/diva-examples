package com.diva.user.api.routes

import com.diva.models.api.ApiResponse
import com.diva.models.api.user.permissions.dtos.DeleteUserPermissionDto
import com.diva.models.api.user.permissions.dtos.UserPermissionDto
import com.diva.user.api.handler.UserPermissionsHandler
import com.diva.util.respond
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.put
import io.ktor.server.routing.route
import org.koin.ktor.ext.inject

internal fun Route.userPermissionsHandler() {
    val handler: UserPermissionsHandler by inject()
    route("/permissions") {
        // TODO: block access
        get("/{userId}") {
            val userId: String = call.pathParameters["userId"] ?: return@get call.respond(
                HttpStatusCode.BadRequest,
                ApiResponse<Nothing>(message = "Missing id")
            )
            handler.getPermissions(userId).respond(call)
        }
        post {
            val dto: UserPermissionDto = call.receive()
            handler.createPermission(dto).respond(call)
        }
        put {
            val dto: UserPermissionDto = call.receive()
            handler.updatePermission(dto).respond(call)
        }
        delete {
            val dto: DeleteUserPermissionDto = call.receive()
            handler.deletePermission(dto).respond(call)
        }
    }
}
