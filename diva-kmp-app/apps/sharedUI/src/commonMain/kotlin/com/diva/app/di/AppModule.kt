package com.diva.app.di

import com.diva.app.config.AppConfig
import com.diva.app.di.database.databaseModule
import com.diva.app.di.network.networkModule
import com.diva.auth.di.authModule
import com.diva.user.di.userModule
import io.github.juevigrace.diva.network.client.config.DivaClientConfig
import io.ktor.client.plugins.logging.LogLevel
import org.koin.core.module.Module
import org.koin.dsl.module

fun appModule(config: AppConfig): Module {
    return module {
        includes(
            databaseModule(),
            networkModule(
                DivaClientConfig(
                    baseUrl = config.baseUrl,
                    logLevel = if (config.debug) LogLevel.ALL else LogLevel.NONE
                )
            ),
        )
        includes(authModule(), userModule())
    }
}
