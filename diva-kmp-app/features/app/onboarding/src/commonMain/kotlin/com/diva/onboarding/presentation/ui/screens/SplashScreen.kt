package com.diva.onboarding.presentation.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.diva.core.resources.resources.generated.resources.Res
import com.diva.core.resources.resources.generated.resources.app
import com.diva.core.resources.resources.generated.resources.puerro
import com.diva.ui.navigation.Destination
import com.diva.ui.navigation.OnboardingDestination
import io.github.juevigrace.diva.ui.components.layout.Screen
import io.github.juevigrace.diva.ui.navigation.Navigator
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject

@Composable
fun SplashScreen() {
    val navigator: Navigator<Destination> = koinInject()
    LaunchedEffect(Unit) {
        delay(1500)
        navigator.replaceTop(OnboardingDestination)
    }

    Screen { innerPadding ->
        Box(
            modifier = Modifier.padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(Res.drawable.puerro),
                contentDescription = stringResource(Res.string.app)
            )
        }
    }
}
