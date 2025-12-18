package com.diva.app.di

import com.diva.app.di.database.databaseModule
import com.diva.auth.di.authModule
import org.koin.core.module.Module
import org.koin.dsl.module

fun appModule(): Module {
    return module {
        includes(
            databaseModule()
        )
        includes(authModule())
    }
}
