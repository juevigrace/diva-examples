package io.github.juevigrace.diva.app

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.diva.ui.navigation.Navigation
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
        Navigation(
            modifier = Modifier.fillMaxSize()
        )
    }
}
