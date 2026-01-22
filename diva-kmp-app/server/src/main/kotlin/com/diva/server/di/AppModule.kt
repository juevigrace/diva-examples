package com.diva.server.di

import com.diva.auth.di.authModule
import com.diva.chat.di.chatModule
import com.diva.collection.di.collectionModule
import com.diva.media.di.mediaModule
import com.diva.permissions.di.permissionsModule
import com.diva.social.di.socialModule
import com.diva.user.di.userModule
import com.diva.verification.di.verificationModule
import org.koin.core.module.Module
import org.koin.dsl.module

fun appModule(): Module {
    return module {
        includes(
            authModule(),
            chatModule(),
            collectionModule(),
            mediaModule(),
            permissionsModule(),
            socialModule(),
            userModule(),
            verificationModule()
        )
    }
}
