package com.diva.app.di.network

import io.github.juevigrace.diva.network.client.DivaClient
import io.github.juevigrace.diva.network.client.config.DivaClientConfig
import io.github.juevigrace.diva.network.client.factory.DivaClientFactory
import org.koin.core.module.Module
import org.koin.dsl.module

expect fun networkFactoryModule(config: DivaClientConfig): Module

fun networkModule(config: DivaClientConfig): Module {
    return module {
        includes(networkFactoryModule(config))
        single<DivaClient> {
            val factory: DivaClientFactory = get()
            factory.create()
        }
    }
}
