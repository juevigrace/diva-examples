package com.diva.server.di.database

import app.cash.sqldelight.db.SqlDriver
import com.diva.database.DivaDB
import io.github.juevigrace.diva.core.models.getOrThrow
import io.github.juevigrace.diva.database.DivaDatabase
import io.github.juevigrace.diva.database.driver.DriverProvider
import io.github.juevigrace.diva.database.driver.Schema
import io.github.juevigrace.diva.database.driver.configuration.DriverConf
import io.github.juevigrace.diva.database.driver.configuration.JvmConf
import io.github.juevigrace.diva.database.driver.factory.JvmDriverProviderFactory
import io.ktor.server.config.ApplicationConfig
import org.koin.core.module.Module
import org.koin.dsl.module

fun databaseModule(config: ApplicationConfig): Module {
    return module {
        single<DriverProvider> {
            JvmDriverProviderFactory(
                JvmConf(
                    DriverConf.PostgresqlDriverConf(
                        host = config.property("database.host").getString(),
                        port = config.property("database.port").getString().toInt(),
                        username = config.property("database.user").getString(),
                        password = config.property("database.password").getString(),
                        database = config.property("database.name").getString(),
                        schema = config.property("database.schema").getString()
                    )
                )
            ).create()
        }

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
