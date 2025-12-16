package com.diva.auth.di

import com.diva.auth.data.service.AuthService
import com.diva.auth.data.service.AuthServiceImpl
import com.diva.auth.database.AuthStorageImpl
import com.diva.auth.database.shared.AuthStorage
import org.koin.core.module.Module
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

fun authModule(): Module {
    return module {
        singleOf(::AuthStorageImpl) { bind<AuthStorage>() }

        singleOf(::AuthServiceImpl) { bind<AuthService>() }
    }
}
