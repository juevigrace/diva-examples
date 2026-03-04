package com.diva.app

import android.app.Application
import android.os.Build
import com.diva.app.di.appModule
import com.diva.models.config.AppConfig
import com.diva.models.config.Flavors
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
                        domain = domain,
                        version = BuildConfig.VERSION_NAME,
                        deviceName = Build.MODEL,
                        agent = "Diva/${BuildConfig.VERSION_NAME} (Linux; Android ${Build.VERSION.SDK_INT}; ${Build.MODEL})"
                    )
                )
            )
            androidContext(this@DivaApp)
            androidLogger()
        }
    }
}
