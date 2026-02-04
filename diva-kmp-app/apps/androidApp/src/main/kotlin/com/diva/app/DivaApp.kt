package com.diva.app

import android.app.Application
import com.diva.app.config.AppConfig
import com.diva.app.config.Flavors
import com.diva.app.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class DivaApp : Application() {
    override fun onCreate() {
        super.onCreate()
        val debug = BuildConfig.DEBUG
        val flavor = Flavors.entries.find { BuildConfig.FLAVOR == it.name } ?: Flavors.PROD
        val domain = BuildConfig.DOMAIN

        startKoin {
            modules(
                appModule(
                    AppConfig(
                        debug = debug,
                        flavor = flavor,
                        domain = domain
                    )
                )
            )
            androidContext(this@DivaApp)
            androidLogger()
        }
    }
}
