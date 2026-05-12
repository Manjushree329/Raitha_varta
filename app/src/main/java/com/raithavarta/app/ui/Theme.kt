package com.raithavarta.app.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val PrimaryGreen = Color(0xFF2E7D32)
val DarkGreen = Color(0xFF1B5E20)
val LightGreen = Color(0xFFE8F5E9)
val SurfaceGreen = Color(0xFFF1F8E9)
val FieldGold = Color(0xFFFFC107)
val SoilBrown = Color(0xFF795548)
val TomatoRed = Color(0xFFD84315)
val SkyBlue = Color(0xFF0288D1)
val TextDark = Color(0xFF212121)
val TextMuted = Color(0xFF6B7280)

private val RaithaColorScheme = lightColorScheme(
    primary = PrimaryGreen,
    onPrimary = Color.White,
    secondary = FieldGold,
    onSecondary = TextDark,
    tertiary = SkyBlue,
    background = Color(0xFFFAFAFA),
    onBackground = TextDark,
    surface = Color.White,
    onSurface = TextDark,
    error = Color(0xFFD32F2F)
)

@Composable
fun RaithaTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = RaithaColorScheme,
        typography = Typography(),
        content = content
    )
}
