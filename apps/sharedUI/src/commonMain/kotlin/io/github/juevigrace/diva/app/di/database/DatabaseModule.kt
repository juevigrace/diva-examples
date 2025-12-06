package io.github.juevigrace.diva.app.di.database

import io.github.juevigrace.diva.core.database.DivaStorage
import io.github.juevigrace.diva.core.database.DivaStorageImpl
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

fun databaseModule(): Module {
    return module {
        includes(driverModule())

        singleOf(::DivaStorageImpl) bind DivaStorage::class
    }
}
