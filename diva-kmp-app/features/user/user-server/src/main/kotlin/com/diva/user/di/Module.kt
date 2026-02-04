package com.diva.user.di

import com.diva.database.user.UserStorage
import com.diva.user.api.handler.UserHandler
import com.diva.user.api.handler.UserHandlerImpl
import com.diva.user.data.UserService
import com.diva.user.data.UserServiceImpl
import com.diva.user.database.UserStorageImpl
import org.koin.core.module.Module
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

fun userModule(): Module {
    return module {
        singleOf(::UserStorageImpl) { bind<UserStorage>() }
        singleOf(::UserServiceImpl) { bind<UserService>() }
        singleOf(::UserHandlerImpl) { bind<UserHandler>() }
    }
}
