package com.diva.app

import android.app.Application
import android.os.Build
import com.diva.app.di.appModule
import com.diva.models.config.AppConfig
import io.github.juevigrace.diva.core.config.Environment
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class DivaApp : Application() {
    override fun onCreate() {
        super.onCreate()
        val debug = BuildConfig.DEBUG
        val environment = Environment.entries.find {
            BuildConfig.FLAVOR == it.name.uppercase()
        } ?: Environment.PRODUCTION
        val domain = BuildConfig.DOMAIN

        startKoin {
            modules(
                appModule(
                    AppConfig(
                        debug = debug,
                        environment = environment,
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
