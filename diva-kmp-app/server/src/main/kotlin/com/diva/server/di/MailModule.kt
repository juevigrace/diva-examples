package com.diva.server.di

import com.diva.mail.KMail
import com.diva.mail.KMailConfig
import io.ktor.server.config.ApplicationConfig
import org.koin.core.module.Module
import org.koin.dsl.module

fun mailModule(config: ApplicationConfig): Module {
    return module {
        single {
            KMail(
                config = KMailConfig(
                    apiKey = config.property("mail.apiKey").getString(),
                    fromEmail = config.property("mail.fromEmail").getString(),
                )
            )
        }
    }
}
