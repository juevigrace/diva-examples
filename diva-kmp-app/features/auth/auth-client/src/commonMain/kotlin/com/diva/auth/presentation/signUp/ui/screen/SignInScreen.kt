package com.diva.auth.presentation.signUp.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.diva.auth.presentation.signUp.state.SignInState
import com.diva.auth.presentation.signUp.viewmodel.SignInViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SignInScreen(
    viewModel: SignInViewModel = koinViewModel()
) {
    val state: SignInState by viewModel.state.collectAsStateWithLifecycle()

    Box(Modifier.fillMaxSize()) {
        Text("sign in")
    }
}
