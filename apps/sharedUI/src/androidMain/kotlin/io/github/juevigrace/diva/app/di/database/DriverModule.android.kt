package io.github.juevigrace.diva.app.di.database

import io.github.juevigrace.diva.database.driver.AndroidConf
import io.github.juevigrace.diva.database.driver.AndroidDriverProviderFactory
import io.github.juevigrace.diva.database.driver.DriverConf
import io.github.juevigrace.diva.database.driver.DriverProvider
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun driverModule(): Module {
    return module {
        single<DriverProvider> {
            AndroidDriverProviderFactory(
                conf = AndroidConf(
                    context = get(),
                    driverConf = DriverConf.SqliteDriverConf("diva.db")
                )
            ).create()
        }
    }
}
