package com.aktiia.core.presentation.designsystem.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

val DarkColorScheme = darkColorScheme(
    primary = AktiiaGreen,
    background = AktiiaBlack,
    surface = AktiiaDarkGray,
    secondary = AktiiaWhite,
    tertiary = AktiiaWhite,
    primaryContainer = AktiiaGreen30,
    onPrimary = AktiiaBlack,
    onBackground = AktiiaWhite,
    onSurface = AktiiaWhite,
    onSurfaceVariant = AktiiaGray,
    error = AktiiaDarkRed,
    errorContainer = AktiiaDarkRed5
)

@Composable
fun AktiiaFoursquareTheme(
    content: @Composable () -> Unit
) {
    val colorScheme = DarkColorScheme
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}