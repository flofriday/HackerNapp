package com.example.hnapp.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = parseColorScheme("#ffffff,#0d100a,#80b380,#1d3027,#70a98d")


private fun parseColorScheme(csvColors: String): ColorScheme {
    fun String.toColor() = Color(android.graphics.Color.parseColor(this))

    val colors =  csvColors
        .split(",")
        .map {s -> s.toColor()}

    val textColor = colors[0]
    val backgroundColor = colors[1]
    val primaryColor = colors[2]
    val secondaryColor = colors[3]
    val accentColor = colors[4]

    return darkColorScheme(
        primary = primaryColor,
        secondary = secondaryColor,
        tertiary = accentColor,
        background = backgroundColor,
        onBackground = textColor,
        onPrimary = backgroundColor,
        onSecondary = textColor,
    )
}


@Composable
fun HNAppTheme(
    darkTheme: Boolean = true,
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = DarkColorScheme
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}