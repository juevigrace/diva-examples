package com.diva.auth.di

import com.diva.auth.data.service.AuthRepositoryImpl
import com.diva.auth.data.shared.AuthLocalSourceImpl
import com.diva.auth.database.AuthStorageImpl
import com.diva.auth.database.shared.AuthStorage
import com.diva.models.LocalSource
import org.koin.core.module.Module
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

fun authModule(): Module {
    return module {
        singleOf(::AuthStorageImpl) { bind<AuthStorage>() }

        singleOf(::AuthLocalSourceImpl) { bind<LocalSource<AuthStorage>>() }

        singleOf(::AuthRepositoryImpl) { bind<AuthRepository>() }
    }
}
