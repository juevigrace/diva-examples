package com.diva.auth.presentation.signUp.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.diva.auth.presentation.signUp.state.SignInState
import com.diva.auth.presentation.signUp.viewmodel.SignInViewModel
import io.github.juevigrace.diva.ui.components.layout.Screen
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SignInScreen(
    viewModel: SignInViewModel = koinViewModel()
) {
    val state: SignInState by viewModel.state.collectAsStateWithLifecycle()

    Screen { innerPadding ->
        Box(
            modifier = Modifier.padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            Text("sign in")
        }
    }
}
