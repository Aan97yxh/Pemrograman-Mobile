package com.example.prak4_viewmodel.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

// ── Color palette ──
object GenshinColors {
    val Background    = Color(0xFF1A1A2E)
    val CardBackground = Color(0xFF252542)
    val Surface       = Color(0xFF2E2E50)
    val Primary       = Color(0xFF4A90A4)
    val TextPrimary   = Color(0xFFFFFFFF)
    val TextSecondary = Color(0xFFB0B0C8)
    val Accent        = Color(0xFFA0C4FF)
    val RarityGold    = Color(0xFFFFD700)
    val Divider       = Color(0xFF3A3A5C)
}

// ── CompositionLocal ──
data class GenshinColorScheme(
    val background: Color    = GenshinColors.Background,
    val card: Color          = GenshinColors.CardBackground,
    val surface: Color       = GenshinColors.Surface,
    val primary: Color       = GenshinColors.Primary,
    val textPrimary: Color   = GenshinColors.TextPrimary,
    val textSecondary: Color = GenshinColors.TextSecondary,
    val accent: Color        = GenshinColors.Accent,
    val rarityGold: Color    = GenshinColors.RarityGold,
    val divider: Color       = GenshinColors.Divider
)

val LocalGenshinColors = staticCompositionLocalOf { GenshinColorScheme() }

object GenshinTheme {
    val colors: GenshinColorScheme
        @Composable get() = LocalGenshinColors.current
}

@Composable
fun GenshinTheme(content: @Composable () -> Unit) {
    CompositionLocalProvider(
        LocalGenshinColors provides GenshinColorScheme()
    ) {
        content()
    }
}
