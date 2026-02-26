package com.diva.auth.presentation.components.navigation

import androidx.navigation3.runtime.EntryProviderScope
import com.diva.auth.presentation.signUp.ui.screen.SignUpScreen
import com.diva.auth.presentation.signIn.ui.screen.SignInScreen
import com.diva.ui.navigation.Destination
import com.diva.ui.navigation.SignInDestination
import com.diva.ui.navigation.SignUpDestination

fun EntryProviderScope<Destination>.authEntries() {
    entry<SignInDestination> {
        SignInScreen()
    }
    entry<SignUpDestination> {
        SignUpScreen()
    }
}
