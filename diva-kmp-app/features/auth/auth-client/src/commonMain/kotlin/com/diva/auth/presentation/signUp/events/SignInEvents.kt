package com.diva.auth.presentation.signUp.events

import com.diva.ui.navigation.arguments.ForgotAction
import com.diva.ui.models.SocialProvider

sealed interface SignInEvents {
    /* Input */
    data class OnUsernameChanged(val email: String) : SignInEvents
    data class OnPasswordChanged(val password: String) : SignInEvents

    /* Navigation */
    data class OnForgot(val action: ForgotAction) : SignInEvents
    data object OnSignUp : SignInEvents

    /* Actions */
    data class OnSocialLogin(val provider: SocialProvider) : SignInEvents
    data object OnSubmit : SignInEvents
}
