package io.github.juevigrace.diva.app.di

import io.github.juevigrace.diva.app.di.database.databaseModule
import org.koin.core.module.Module
import org.koin.dsl.module

fun appModule(): Module {
    return module {
        includes(
            databaseModule()
        )
    }
}