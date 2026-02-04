package com.diva.server.config

import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.calllogging.CallLogging
import io.ktor.server.request.path
import org.slf4j.event.Level

fun Application.configureAdministration() {
    val dev = environment.config.property("ktor.development").getString().toBoolean()
    install(CallLogging) {
        level = if (dev) Level.DEBUG else Level.INFO
        filter { call -> call.request.path().startsWith("/") }
    }
}
