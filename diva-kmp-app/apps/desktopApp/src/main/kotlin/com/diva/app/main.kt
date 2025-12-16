package com.diva.app

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.diva.app.di.appModule
import io.github.juevigrace.diva.di.DivaDi

fun main() = application {
    DivaDi.start {
        modules(appModule())
    }
    Window(
        onCloseRequest = ::exitApplication,
        title = "Diva",
    ) {
        App()
    }
}
