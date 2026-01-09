package com.diva.app.di.network

import io.github.juevigrace.diva.network.client.config.DivaClientConfig
import io.github.juevigrace.diva.network.client.factory.DivaClientFactory
import io.github.juevigrace.diva.network.client.factory.JsDivaClientFactory
import io.ktor.client.engine.js.Js
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun networkFactoryModule(config: DivaClientConfig): Module {
    return module {
        single<DivaClientFactory> {
            JsDivaClientFactory(Js, config)
        }
    }
}
