package com.diva.auth.presentation.signIn.viewmodel

import com.diva.auth.data.AuthRepository
import com.diva.auth.data.validation.SignInValidation
import com.diva.auth.data.validation.SignInValidator
import com.diva.auth.presentation.signIn.events.SignInEvents
import com.diva.auth.presentation.signIn.state.SignInState
import com.diva.models.auth.SignInForm
import com.diva.ui.messages.toToast
import com.diva.ui.models.SocialProvider
import com.diva.ui.navigation.Destination
import com.diva.ui.navigation.ForgotDestination
import com.diva.ui.navigation.HomeDestination
import com.diva.ui.navigation.SignUpDestination
import com.diva.ui.navigation.arguments.ForgotAction
import io.github.juevigrace.diva.core.Option
import io.github.juevigrace.diva.core.fold
import io.github.juevigrace.diva.ui.navigation.Navigator
import io.github.juevigrace.diva.ui.toast.Toaster
import io.github.juevigrace.diva.ui.viewmodel.DivaViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignInViewModel(
    private val repository: AuthRepository,
    private val navigator: Navigator<Destination>,
    private val toaster: Toaster,
) : DivaViewModel() {
    private val formState = MutableStateFlow(SignInForm())

    private val formValidationState = MutableStateFlow(SignInValidation())

    private val combinedValidationState: StateFlow<SignInValidation> = combine(
        formState,
        formValidationState,
    ) { form, validation ->
        val valid = SignInValidator.validate(form)
        validation.copy(
            usernameError = if (validation.showUsernameError) valid.usernameError else Option.None,
            passwordError = if (validation.showPasswordError) valid.passwordError else Option.None,
        )
    }.stateIn(
        scope = scope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = formValidationState.value
    )

    private val _state: MutableStateFlow<SignInState> = MutableStateFlow(SignInState())
    val state: StateFlow<SignInState> = combine(
        _state,
        formState,
        combinedValidationState,
    ) { state, form, validation ->
        state.copy(
            signInForm = form,
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

    fun onEvent(event: SignInEvents) {
        when (event) {
            is SignInEvents.OnPasswordChanged -> passwordChanged(event.password)
            is SignInEvents.OnUsernameChanged -> usernameChanged(event.email)
            is SignInEvents.OnSocialLogin -> handleSocialLogin(event.provider)
            is SignInEvents.OnForgot -> navigateToForgot(event.action)
            SignInEvents.OnSignUp -> navigateToSignUp()
            SignInEvents.OnSubmit -> submit()
        }
    }

    private fun usernameChanged(value: String) {
        formState.update { state -> state.copy(username = value) }
        formValidationState.update { state -> state.copy(showUsernameError = true) }
    }

    private fun passwordChanged(value: String) {
        formState.update { state -> state.copy(password = value) }
        formValidationState.update { state -> state.copy(showPasswordError = true) }
    }

    private fun handleSocialLogin(provider: SocialProvider) {
        TODO()
    }

    private fun navigateToForgot(action: ForgotAction) {
        navigator.navigate(ForgotDestination(action))
    }

    private fun navigateToSignUp() {
        navigator.navigate(SignUpDestination)
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
            repository.signIn(_state.value.signInForm).collect { result ->
                result.fold(
                    onFailure = { err ->
                        toaster.show(err.toToast())
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
                        navigator.replaceAll(HomeDestination)
                    }
                )
            }
        }
    }
}
