package com.diva.app.presentation.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.diva.app.presentation.viewmodel.AppViewModel
import com.diva.ui.theme.AppTypography
import com.diva.ui.theme.darkScheme
import com.diva.ui.theme.lightScheme
import io.github.juevigrace.diva.ui.theme.DivaTheme
import io.github.juevigrace.diva.ui.theme.DivaThemeConfig
import io.github.juevigrace.diva.ui.theme.ThemeScheme
import io.github.juevigrace.diva.ui.toast.LocalToaster
import io.github.juevigrace.diva.ui.toast.Toaster
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun App() {
    val viewModel: AppViewModel = koinViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()

    val toaster: Toaster = koinInject()

    DivaTheme(
        config = DivaThemeConfig(
            themeScheme = ThemeScheme(
                light = lightScheme,
                dark = darkScheme
            ),
            typography = AppTypography
        ),
    ) {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            CompositionLocalProvider(
                LocalToaster provides toaster
            ) {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    snackbarHost = { Toaster() },
                ) { innerPadding ->
                    Box(
                        modifier = Modifier.padding(innerPadding),
                        contentAlignment = Alignment.Center
                    ) {
                    }
                }
            }
        }
    }
}
