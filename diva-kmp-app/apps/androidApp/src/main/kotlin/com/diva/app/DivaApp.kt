package com.diva.app

import android.app.Application
import com.diva.app.config.AppConfig
import com.diva.app.di.appModule
import io.github.juevigrace.diva.di.DivaDi
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger

class DivaApp : Application() {
    override fun onCreate() {
        super.onCreate()
        DivaDi.start {
            modules(appModule(AppConfig()))
            androidContext(this@DivaApp)
            androidLogger()
        }
    }
}
