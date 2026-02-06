package com.diva.ui.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface Destination : NavKey {
    @Serializable
    data object Home : Destination

    @Serializable
    data object SignIn : Destination

    @Serializable
    data object SignUp : Destination

    @Serializable
    data object ForgotPassword : Destination
}
