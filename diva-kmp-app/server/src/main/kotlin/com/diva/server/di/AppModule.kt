package com.diva.server.di

import com.diva.auth.di.authModule
import com.diva.user.di.userModule
import com.diva.verification.di.verificationModule
import org.koin.core.module.Module
import org.koin.dsl.module

fun appModule(): Module {
    return module {
        includes(authModule(), userModule(), verificationModule())
    }
}
