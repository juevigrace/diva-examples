package com.diva.server.config

import com.diva.server.di.appModule
import com.diva.server.di.database.databaseModule
import io.ktor.server.application.Application
import io.ktor.server.application.install
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun Application.configureFrameworks() {
    install(Koin) {
        slf4jLogger()
        modules(appModule(), databaseModule(environment.config))
    }
}
