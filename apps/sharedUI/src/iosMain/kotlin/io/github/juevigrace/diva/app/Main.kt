package io.github.juevigrace.diva.app

import androidx.compose.ui.window.ComposeUIViewController
import io.github.juevigrace.diva.app.di.appModule
import io.github.juevigrace.diva.di.DivaDi
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController = ComposeUIViewController {
    DivaDi.start {
        modules(appModule())
    }

    App()
}
