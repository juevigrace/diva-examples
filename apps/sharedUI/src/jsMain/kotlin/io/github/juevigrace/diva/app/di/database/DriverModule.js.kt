package io.github.juevigrace.diva.app.di.database

import io.github.juevigrace.diva.database.driver.DriverProvider
import io.github.juevigrace.diva.database.driver.JsDriverProviderFactory
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun driverModule(): Module {
    return module {
        single<DriverProvider> { JsDriverProviderFactory().create() }
    }
}
