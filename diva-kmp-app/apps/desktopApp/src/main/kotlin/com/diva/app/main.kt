package com.diva.app

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.diva.app.config.AppConfig
import com.diva.app.config.Flavors
import com.diva.app.di.appModule
import org.koin.core.context.GlobalContext.startKoin

fun main(args: Array<String>) = application {
    // Parse arguments
    val argsMap: Map<String, String> = args.let { arguments ->
        mutableMapOf<String, String>().apply {
            for (i in arguments.indices) {
                when {
                    arguments[i].startsWith("--") && i + 1 < arguments.size -> {
                        put(arguments[i].substring(2), arguments[i + 1])
                    }
                    arguments[i].startsWith("-") && i + 1 < arguments.size -> {
                        put(arguments[i].substring(1), arguments[i + 1])
                    }
                }
            }
        }.toMap()
    }

    // Access parsed arguments
    val debug: Boolean = argsMap["-D"]?.toBoolean() ?: false
    val flavor: Flavors = Flavors.entries.find { it.name == argsMap["-F"] } ?: Flavors.PROD

    startKoin {
        modules(
            appModule(
                AppConfig(
                    debug = debug,
                    flavor = flavor
                )
            )
        )
    }
    Window(
        onCloseRequest = ::exitApplication,
        title = "Diva",
    ) {
        App()
    }
}
