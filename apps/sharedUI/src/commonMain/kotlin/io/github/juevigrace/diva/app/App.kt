package io.github.juevigrace.diva.app

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.github.juevigrace.diva.core.ui.components.navigation.Navigation
import io.github.juevigrace.diva.core.ui.theme.AppTypography
import io.github.juevigrace.diva.core.ui.theme.darkScheme
import io.github.juevigrace.diva.core.ui.theme.lightScheme
import io.github.juevigrace.diva.ui.DivaApp
import io.github.juevigrace.diva.ui.theme.DivaTheme
import io.github.juevigrace.diva.ui.theme.DivaThemeConfig
import io.github.juevigrace.diva.ui.theme.ThemeScheme

@Composable
fun App() {
    DivaApp(
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
        Navigation(
            modifier = Modifier.fillMaxSize()
        )
    }
}
