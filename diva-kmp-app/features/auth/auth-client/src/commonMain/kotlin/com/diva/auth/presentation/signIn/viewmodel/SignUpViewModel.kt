package com.diva.auth.presentation.signIn.viewmodel

import com.diva.auth.data.AuthRepository
import com.diva.auth.presentation.signIn.events.SignUpEvents
import com.diva.auth.presentation.signIn.state.SignUpState
import com.diva.models.auth.SignUpForm
import io.github.juevigrace.diva.ui.viewmodel.DivaViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SignUpViewModel(
    private val repository: AuthRepository
) : DivaViewModel() {
    private val formState = MutableStateFlow(SignUpForm())

    private val _state = MutableStateFlow(SignUpState())
    val state = _state.asStateFlow()

    fun onEvent(event: SignUpEvents) {
        when (event) {
            is SignUpEvents.OnBirthDateChanged -> TODO()
            is SignUpEvents.OnConfirmPasswordChanged -> TODO()
            is SignUpEvents.OnEmailChanged -> TODO()
            is SignUpEvents.OnFirstNameChanged -> TODO()
            is SignUpEvents.OnLastNameChanged -> TODO()
            is SignUpEvents.OnPasswordChanged -> TODO()
            is SignUpEvents.OnPhoneChanged -> TODO()
            SignUpEvents.OnSubmit -> TODO()
            is SignUpEvents.OnUsernameChanged -> TODO()
            SignUpEvents.TogglePrivacyPolicy -> TODO()
            SignUpEvents.ToggleTerms -> TODO()
        }
    }
}
