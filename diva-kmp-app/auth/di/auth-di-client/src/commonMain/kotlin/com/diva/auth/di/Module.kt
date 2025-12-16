package com.diva.auth.di

import com.diva.auth.api.client.AuthNetworkClient
import com.diva.auth.api.client.AuthNetworkClientImpl
import com.diva.auth.data.AuthRepository
import com.diva.auth.data.AuthRepositoryImpl
import com.diva.auth.database.AuthStorageImpl
import com.diva.auth.database.shared.AuthStorage
import org.koin.core.module.Module
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

fun authModule(): Module {
    return module {
        singleOf(::AuthStorageImpl) { bind<AuthStorage>() }

        singleOf(::AuthNetworkClientImpl) { bind<AuthNetworkClient>() }

        singleOf(::AuthRepositoryImpl) { bind<AuthRepository>() }
    }
}
