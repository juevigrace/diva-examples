package com.diva.auth.presentation.signIn.events

sealed interface SignUpEvents {
    data class OnFirstNameChanged(val value: String) : SignUpEvents
    data class OnLastNameChanged(val value: String) : SignUpEvents
    data class OnPhoneChanged(val value: String) : SignUpEvents
    data class OnBirthDateChanged(val value: Long) : SignUpEvents
    data class OnEmailChanged(val value: String) : SignUpEvents
    data class OnUsernameChanged(val value: String) : SignUpEvents
    data class OnPasswordChanged(val value: String) : SignUpEvents
    data class OnConfirmPasswordChanged(val value: String) : SignUpEvents
    data object TogglePrivacyPolicy : SignUpEvents
    data object ToggleTerms : SignUpEvents
    data object OnSubmit : SignUpEvents
}
