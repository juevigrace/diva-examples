package com.diva.app.di.network

import com.diva.models.config.AppConfig
import io.github.juevigrace.diva.network.client.DivaClient
import io.github.juevigrace.diva.network.client.factory.DivaClientFactory
import io.ktor.client.plugins.defaultRequest
import org.koin.core.module.Module
import org.koin.dsl.module

expect fun networkFactoryModule(): Module

fun networkModule(config: AppConfig): Module {
    return module {
        includes(networkFactoryModule())
        single<DivaClient> {
            val factory: DivaClientFactory = get()
            val client = factory.create()
            client.config {
                defaultRequest {
                    url(config.baseUrl)
                }
            }
            client
        }
    }
}
