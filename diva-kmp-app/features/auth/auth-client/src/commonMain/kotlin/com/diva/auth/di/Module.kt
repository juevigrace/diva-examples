package com.diva.auth.di

import com.diva.auth.api.client.AuthNetworkClient
import com.diva.auth.api.client.AuthNetworkClientImpl
import com.diva.auth.data.AuthRepository
import com.diva.auth.data.AuthRepositoryImpl
import com.diva.database.session.SessionStorage
import com.diva.session.database.SessionStorageImpl
import org.koin.core.module.Module
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

fun authModule(): Module {
    return module {
        singleOf(::SessionStorageImpl) { bind<SessionStorage>() }

        singleOf(::AuthNetworkClientImpl) { bind<AuthNetworkClient>() }

        singleOf(::AuthRepositoryImpl) { bind<AuthRepository>() }
    }
}
