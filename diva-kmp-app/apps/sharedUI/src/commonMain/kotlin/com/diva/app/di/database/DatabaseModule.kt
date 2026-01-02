package com.diva.app.di.database

import app.cash.sqldelight.db.SqlDriver
import com.diva.database.DivaDB
import io.github.juevigrace.diva.core.models.getOrThrow
import io.github.juevigrace.diva.database.DivaDatabase
import io.github.juevigrace.diva.database.driver.DriverProvider
import io.github.juevigrace.diva.database.driver.Schema
import org.koin.core.module.Module
import org.koin.dsl.module

fun databaseModule(): Module {
    return module {
        includes(driverModule())

        single<SqlDriver> {
            val provider: DriverProvider = get()
            provider.createDriver(Schema.Async(DivaDB.Schema)).getOrThrow()
        }

        single<DivaDatabase<DivaDB>> {
            val driver: SqlDriver = get()
            DivaDatabase(
                driver = driver,
                db = DivaDB(driver),
            )
        }
    }
}
