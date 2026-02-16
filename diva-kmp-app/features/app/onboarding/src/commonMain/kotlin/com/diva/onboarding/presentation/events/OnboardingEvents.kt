package com.diva.onboarding.presentation.events

sealed interface OnboardingEvents {
    data object OnNavigateToSignIn : OnboardingEvents
    data object OnNavigateToSignUp : OnboardingEvents
}
