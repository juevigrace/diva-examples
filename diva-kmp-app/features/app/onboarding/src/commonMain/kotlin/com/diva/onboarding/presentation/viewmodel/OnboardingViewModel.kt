package com.diva.onboarding.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.diva.onboarding.presentation.events.OnboardingEvents
import com.diva.ui.navigation.Destination
import com.diva.ui.navigation.SignInDestination
import com.diva.ui.navigation.SignUpDestination
import io.github.juevigrace.diva.ui.navigation.Navigator

class OnboardingViewModel(
    private val navigator: Navigator<Destination>
) : ViewModel() {
    fun onEvent(event: OnboardingEvents) {
        when (event) {
            OnboardingEvents.OnNavigateToSignIn -> navigator.navigate(SignInDestination)
            OnboardingEvents.OnNavigateToSignUp -> navigator.navigate(SignUpDestination)
        }
    }
}
