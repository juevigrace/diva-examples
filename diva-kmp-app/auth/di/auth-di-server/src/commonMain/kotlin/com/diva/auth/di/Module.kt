package com.diva.auth.di

import com.diva.auth.data.service.AuthService
import com.diva.auth.data.service.AuthServiceImpl
import com.diva.session.database.SessionStorageImpl
import com.diva.session.database.shared.SessionStorage
import org.koin.core.module.Module
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

fun authModule(): Module {
    return module {
        singleOf(::SessionStorageImpl) { bind<SessionStorage>() }

        singleOf(::AuthServiceImpl) { bind<AuthService>() }
    }
}
