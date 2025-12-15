package com.diva.app.di.database

import com.diva.database.DivaDB
import io.github.juevigrace.diva.database.Storage
import io.github.juevigrace.diva.database.driver.Schema
import org.koin.core.module.Module
import org.koin.dsl.module

fun databaseModule(): Module {
    return module {
        includes(driverModule())

        single<Storage<DivaDB>> {
            Storage(
                provider = get(),
                schema = Schema.Async(DivaDB.Schema),
                onDriverCreated = { driver ->
                    DivaDB(driver)
                },
            )
        }
    }
}
