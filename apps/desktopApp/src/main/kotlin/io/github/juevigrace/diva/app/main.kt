package io.github.juevigrace.diva.app

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import io.github.juevigrace.diva.app.di.appModule
import io.github.juevigrace.diva.di.DivaApp

fun main() = application {
    DivaApp.start {
        modules(appModule())
    }
    Window(
        onCloseRequest = ::exitApplication,
        title = "Diva",
    ) {
        App()
    }
}
