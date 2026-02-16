package com.diva.onboarding.presentation.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.diva.onboarding.presentation.events.OnboardingEvents
import com.diva.onboarding.presentation.viewmodel.OnboardingViewModel
import io.github.juevigrace.diva.ui.components.layout.Screen
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun OnboardingScreen(
    viewModel: OnboardingViewModel = koinViewModel()
) {
    Screen { innerPadding ->
        Box(
            modifier = Modifier.padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            Button(onClick = {
                viewModel.onEvent(OnboardingEvents.OnNavigateToSignIn)
            }) {
                Text(text = "Sign In")
            }
            Button(onClick = {
                viewModel.onEvent(OnboardingEvents.OnNavigateToSignUp)
            }) {
                Text(text = "Sign Up")
            }
        }
    }
}
