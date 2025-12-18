package com.diva.auth.di

import com.diva.auth.api.client.AuthNetworkClient
import com.diva.auth.api.client.AuthNetworkClientImpl
import com.diva.auth.data.AuthRemoteSourceImpl
import com.diva.auth.data.AuthRepositoryImpl
import com.diva.auth.data.shared.AuthLocalSourceImpl
import com.diva.auth.database.AuthStorageImpl
import com.diva.auth.database.shared.AuthStorage
import com.diva.models.LocalSource
import com.diva.models.RemoteSource
import org.koin.core.module.Module
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

fun authModule(): Module {
    return module {
        singleOf(::AuthStorageImpl) { bind<AuthStorage>() }

        singleOf(::AuthLocalSourceImpl) { bind<LocalSource<AuthStorage>>() }

        singleOf(::AuthNetworkClientImpl) { bind<AuthNetworkClient>() }

        singleOf(::AuthRemoteSourceImpl) { bind<RemoteSource<AuthNetworkClient>>() }

        singleOf(::AuthRepositoryImpl) { bind<AuthRepository>() }
    }
}
