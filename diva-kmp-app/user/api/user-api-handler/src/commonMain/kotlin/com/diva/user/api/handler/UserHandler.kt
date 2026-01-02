package com.diva.user.api.handler

import com.diva.user.data.UserService
import io.ktor.server.routing.Routing
import io.ktor.server.routing.route
import org.koin.ktor.ext.inject

fun Routing.userApiHandler() {
    val service: UserService by inject()

    route("/user") {

    }
}
