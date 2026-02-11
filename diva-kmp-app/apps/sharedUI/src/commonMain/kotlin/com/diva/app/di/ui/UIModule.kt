package com.diva.app.di.ui

import io.github.juevigrace.diva.ui.toast.Toaster
import org.koin.core.module.Module
import org.koin.dsl.module

fun uiModule(): Module {
    return module {
        single<Toaster> {
            Toaster.invoke()
        }
    }
}
