package com.diva.auth.presentation.signUp.viewmodel

import com.diva.auth.data.AuthRepository
import com.diva.ui.navigation.arguments.ForgotAction
import com.diva.auth.presentation.signUp.events.SignInEvents
import com.diva.auth.presentation.signUp.state.SignInState
import com.diva.models.auth.SignInForm
import com.diva.ui.models.SocialProvider
import io.github.juevigrace.diva.core.fold
import io.github.juevigrace.diva.ui.viewmodel.DivaViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignInViewModel(
    private val repository: AuthRepository
) : DivaViewModel() {
    private val formState = MutableStateFlow(SignInForm())

    private val _state = MutableStateFlow(SignInState())
    val state = _state.asStateFlow()

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
        formState.update { state ->
            state.copy(
                username = value,
            )
        }
    }

    private fun passwordChanged(value: String) {
        formState.update { state ->
            state.copy(
                password = value,
            )
        }
    }

    private fun handleSocialLogin(provider: SocialProvider) {}

    fun navigateToForgot(action: ForgotAction) {}

    fun navigateToSignUp() {}

    private fun submit() {
        _state.update { state ->
            state.copy(
                submitLoading = true,
                submitEnabled = false,
            )
        }
        scope.launch {
            repository.signIn(_state.value.signInForm).collect { result ->
                result.fold(
                    onFailure = { err ->
                        _state.update { state ->
                            state.copy(
                                submitLoading = false,
                                submitEnabled = true,
                            )
                        }
                    },
                    onSuccess = {}
                )
            }
        }
    }
}
