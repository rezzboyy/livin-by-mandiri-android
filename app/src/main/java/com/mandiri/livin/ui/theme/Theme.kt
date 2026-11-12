package com.mandiri.livin.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = LivinBlueMid,
    secondary = LivinGoldPrimary,
    tertiary = LivinDeepNavy,
    background = LivinBlueBottom,
    surface = LivinDeepNavy,
    onPrimary = LivinWhite,
    onSecondary = LivinDeepNavy,
    onBackground = LivinWhite,
    onSurface = LivinWhite
)

@Composable
fun LivinTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}
