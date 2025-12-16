package com.diva.app

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.diva.ui.theme.AppTypography
import com.diva.ui.theme.darkScheme
import com.diva.ui.theme.lightScheme
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
        Text(text = "Hello")
    }
}
