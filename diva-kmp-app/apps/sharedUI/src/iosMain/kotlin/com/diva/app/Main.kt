package com.diva.app

import androidx.compose.ui.window.ComposeUIViewController
import com.diva.app.config.AppConfig
import com.diva.app.di.appModule
import org.koin.core.context.startKoin
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController = ComposeUIViewController {
    startKoin {
        modules(appModule(AppConfig()))
    }

    App()
}
