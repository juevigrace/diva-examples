package com.diva.auth.domain.models

import kotlinx.serialization.Serializable

@Serializable
sealed interface ForgotAction {
    @Serializable
    data object OnForgotPassword : ForgotAction
}
