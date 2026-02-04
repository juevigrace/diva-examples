package com.diva.user.api.routes

import com.diva.models.api.ApiResponse
import com.diva.models.api.user.permissions.dtos.UserPermissionDto
import com.diva.models.server.AUTH_JWT_KEY
import com.diva.user.data.UserPermissionsService
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

internal fun Route.userPermissionsHandler() {
    val service: UserPermissionsService by inject()

    route("/permissions") {
        get {
            val idStr: String = call.pathParameters["id"] ?: return@get call.respond(
                HttpStatusCode.BadRequest,
                ApiResponse<Nothing>(message = "Missing id")
            )
            service.getPermissions(idStr).respond(call)
        }
        route("/{permissionId}") {
            get {
                val idStr: String = call.pathParameters["id"] ?: return@get call.respond(
                    HttpStatusCode.BadRequest,
                    ApiResponse<Nothing>(message = "Missing id")
                )
                val permissionId: String = call.pathParameters["permissionId"] ?: return@get call.respond(
                    HttpStatusCode.BadRequest,
                    ApiResponse<Nothing>(message = "Missing id")
                )
                service.getPermission(idStr, permissionId).respond(call)
            }
            delete {
                val idStr: String = call.pathParameters["id"] ?: return@delete call.respond(
                    HttpStatusCode.BadRequest,
                    ApiResponse<Nothing>(message = "Missing id")
                )
                val permissionId: String = call.pathParameters["permissionId"] ?: return@delete call.respond(
                    HttpStatusCode.BadRequest,
                    ApiResponse<Nothing>(message = "Missing id")
                )
                service.deletePermission(idStr, permissionId).respond(call)
            }
        }
        authenticate(AUTH_JWT_KEY) {
            post {
                val dto: UserPermissionDto = call.receive()
                service.createPermission(dto).respond(call)
            }
            put {
                val dto: UserPermissionDto = call.receive()
                service.updatePermission(dto).respond(call)
            }
        }
    }
}
