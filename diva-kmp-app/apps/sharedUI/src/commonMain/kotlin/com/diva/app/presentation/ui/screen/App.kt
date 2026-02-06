package com.diva.app.presentation.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.diva.app.presentation.viewmodel.AppViewModel
import com.diva.ui.theme.AppTypography
import com.diva.ui.theme.darkScheme
import com.diva.ui.theme.lightScheme
import io.github.juevigrace.diva.ui.DivaApp
import io.github.juevigrace.diva.ui.theme.DivaTheme
import io.github.juevigrace.diva.ui.theme.DivaThemeConfig
import io.github.juevigrace.diva.ui.theme.ThemeScheme
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun App() {
    val viewModel: AppViewModel = koinViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()

    DivaApp(
        startDestination = state.startDestination,
        theme = { content ->
            DivaTheme(
                config = DivaThemeConfig(
                    themeScheme = ThemeScheme(
                        light = lightScheme,
                        dark = darkScheme
                    ),
                    typography = AppTypography
                ),
                content = content
            )
        }
    ) {
    }
}
