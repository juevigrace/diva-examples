package com.diva.auth.presentation.signUp.viewmodel

import com.diva.auth.data.AuthRepository
import com.diva.auth.data.validation.SignUpValidation
import com.diva.auth.data.validation.SignUpValidator
import com.diva.auth.presentation.signUp.events.SignUpEvents
import com.diva.auth.presentation.signUp.state.SignUpState
import com.diva.models.auth.SignUpForm
import com.diva.ui.navigation.Destination
import io.github.juevigrace.diva.core.Option
import io.github.juevigrace.diva.core.fold
import io.github.juevigrace.diva.ui.navigation.Navigator
import io.github.juevigrace.diva.ui.viewmodel.DivaViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignUpViewModel(
    private val repository: AuthRepository,
    private val navigator: Navigator<Destination>
) : DivaViewModel() {
    private val formState = MutableStateFlow(SignUpForm())

    private val formValidationState = MutableStateFlow(SignUpValidation())

    private val combinedValidationState: StateFlow<SignUpValidation> = combine(
        formState,
        formValidationState,
    ) { form, validation ->
        val valid = SignUpValidator.validate(form)
        validation.copy(
            emailError = if (validation.showEmailError) valid.emailError else Option.None,
            usernameError = if (validation.showUsernameError) valid.usernameError else Option.None,
            passwordError = if (validation.showPasswordError) valid.passwordError else Option.None,
            confirmPasswordError = if (validation.showConfirmPasswordError) valid.confirmPasswordError else Option.None,
            birthDateError = if (validation.showBirthDateError) valid.birthDateError else Option.None,
            phoneError = if (validation.showPhoneError) valid.phoneError else Option.None,
            termsError = if (validation.showTermsError) valid.termsError else Option.None,
            privacyPolicyError = if (validation.showPrivacyPolicyError) valid.privacyPolicyError else Option.None,
        )
    }.stateIn(
        scope = scope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = formValidationState.value
    )

    private val _state: MutableStateFlow<SignUpState> = MutableStateFlow(SignUpState())
    val state: StateFlow<SignUpState> = combine(
        _state,
        formState,
        combinedValidationState,
    ) { state, form, validation ->
        state.copy(
            signUpForm = form,
            formValidation = validation,
            submitEnabled = validation.valid() && !state.submitLoading
        )
    }
        .distinctUntilChanged()
        .stateIn(
            scope = scope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = _state.value,
        )

    fun onEvent(event: SignUpEvents) {
        when (event) {
            is SignUpEvents.OnAliasNameChanged -> aliasNameChanged(event.value)
            is SignUpEvents.OnBirthDateChanged -> birthDateChanged(event.value)
            is SignUpEvents.OnConfirmPasswordChanged -> confirmPasswordChanged(event.value)
            is SignUpEvents.OnEmailChanged -> emailChanged(event.value)
            is SignUpEvents.OnPasswordChanged -> passwordChanged(event.value)
            is SignUpEvents.OnPhoneChanged -> phoneChanged(event.value)
            is SignUpEvents.OnUsernameChanged -> usernameChanged(event.value)
            SignUpEvents.OnSubmit -> submit()
            SignUpEvents.TogglePrivacyPolicy -> togglePrivacyPolicy()
            SignUpEvents.ToggleTerms -> toggleTerms()
            SignUpEvents.OnNavigateToSignIn -> navigateToSignIn()
        }
    }

    private fun aliasNameChanged(value: String) {
        formState.update { it.copy(alias = value) }
    }

    private fun emailChanged(value: String) {
        formState.update { it.copy(email = value) }
        formValidationState.update { it.copy(showEmailError = true) }
    }

    private fun usernameChanged(value: String) {
        formState.update { it.copy(username = value) }
        formValidationState.update { it.copy(showUsernameError = true) }
    }

    private fun passwordChanged(value: String) {
        formState.update { it.copy(password = value) }
        formValidationState.update { it.copy(showPasswordError = true) }
    }

    private fun confirmPasswordChanged(value: String) {
        formState.update { it.copy(confirmPassword = value) }
        formValidationState.update { it.copy(showConfirmPasswordError = true) }
    }

    private fun phoneChanged(value: String) {
        formState.update { it.copy(phone = value) }
        formValidationState.update { it.copy(showPhoneError = true) }
    }

    private fun birthDateChanged(value: Long) {
        formState.update { it.copy(birthDate = value) }
        formValidationState.update { it.copy(showBirthDateError = true) }
    }

    private fun toggleTerms() {
        formState.update { it.copy(termsAndConditions = !it.termsAndConditions) }
        formValidationState.update { it.copy(showTermsError = true) }
    }

    private fun togglePrivacyPolicy() {
        formState.update { it.copy(privacyPolicy = !it.privacyPolicy) }
        formValidationState.update { it.copy(showPrivacyPolicyError = true) }
    }

    private fun submit() {
        _state.update { state ->
            state.copy(
                submitLoading = true,
                submitEnabled = false,
                submitSuccess = false
            )
        }

        scope.launch {
            repository.signUp(_state.value.signUpForm).collect { result ->
                result.fold(
                    onFailure = { err ->
                        println(err)
                        _state.update { state ->
                            state.copy(
                                submitLoading = false,
                                submitEnabled = true,
                                submitSuccess = false,
                            )
                        }
                    },
                    onSuccess = {
                        _state.update { state ->
                            state.copy(
                                submitLoading = false,
                                submitSuccess = true
                            )
                        }
                        navigator.navigate(Destination.HomeGraph.Home)
                    }
                )
            }
        }
    }

    private fun navigateToSignIn() {
        navigator.pop()
    }
}
