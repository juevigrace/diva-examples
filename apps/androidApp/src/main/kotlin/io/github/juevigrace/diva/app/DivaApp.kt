package io.github.juevigrace.diva.app

import android.app.Application
import io.github.juevigrace.diva.app.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import io.github.juevigrace.diva.di.DivaDi

class DivaApp : Application() {
    override fun onCreate() {
        super.onCreate()
        DivaDi.start {
            modules(appModule())
            androidContext(this@DivaApp)
            androidLogger()
        }
    }
}
