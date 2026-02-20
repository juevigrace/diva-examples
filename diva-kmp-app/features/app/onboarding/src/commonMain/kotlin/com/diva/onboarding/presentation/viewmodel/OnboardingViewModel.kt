package com.diva.onboarding.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.diva.onboarding.presentation.events.OnboardingEvents
import com.diva.onboarding.presentation.state.OnboardingState
import com.diva.ui.navigation.Destination
import com.diva.ui.navigation.SignInDestination
import com.diva.ui.navigation.SignUpDestination
import io.github.juevigrace.diva.ui.navigation.Navigator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class OnboardingViewModel(
    private val navigator: Navigator<Destination>
) : ViewModel() {
    private val _state = MutableStateFlow(OnboardingState())
    val state: StateFlow<OnboardingState> = _state.asStateFlow()

    fun onEvent(event: OnboardingEvents) {
        when (event) {
            OnboardingEvents.OnNavigateToSignIn -> navigator.navigate(SignInDestination)
            OnboardingEvents.OnNavigateToSignUp -> navigator.navigate(SignUpDestination)
            OnboardingEvents.OnNextPage -> nextPage()
            OnboardingEvents.OnPreviousPage -> previousPage()
            OnboardingEvents.OnSkip -> skipToLastPage()
        }
    }

    private fun nextPage() {
        _state.update { state ->
            if (state.page == state.pages.lastIndex) return@update state
            state.copy(page = state.page + 1)
        }
    }

    private fun previousPage() {
        _state.update { state ->
            if (state.page == 0) return@update state
            state.copy(page = state.page - 1)
        }
    }

    private fun skipToLastPage() {
        _state.update { state ->
            state.copy(page = state.pages.lastIndex)
        }
    }
}
