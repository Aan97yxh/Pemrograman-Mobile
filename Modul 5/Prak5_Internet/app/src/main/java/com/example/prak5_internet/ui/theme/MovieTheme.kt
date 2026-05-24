package com.example.prak5_internet.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

object MovieColors {
    val BackgroundDark     = Color(0xFF1A1A2E)
    val CardBackgroundDark = Color(0xFF252542)
    val SurfaceDark        = Color(0xFF2E2E50)
    val TextPrimaryDark    = Color(0xFFFFFFFF)
    val TextSecondaryDark  = Color(0xFFB0B0C8)
    val DividerDark        = Color(0xFF3A3A5C)

    val BackgroundLight     = Color(0xFFF7F5F0)
    val CardBackgroundLight = Color(0xFFFFFFFF)
    val SurfaceLight        = Color(0xFFEFECE5)
    val TextPrimaryLight    = Color(0xFF2C2C2C)
    val TextSecondaryLight  = Color(0xFF757575)
    val DividerLight        = Color(0xFFDFDDD7)

    val Primary    = Color(0xFF4A90A4)
    val Accent     = Color(0xFFA0C4FF)
    val RarityGold = Color(0xFFFFD700)
}

data class MovieColorScheme(
    val background: Color,
    val card: Color,
    val surface: Color,
    val primary: Color,
    val textPrimary: Color,
    val textSecondary: Color,
    val accent: Color,
    val rarityGold: Color,
    val divider: Color
)

private val DarkColorScheme = MovieColorScheme(
    background    = MovieColors.BackgroundDark,
    card          = MovieColors.CardBackgroundDark,
    surface       = MovieColors.SurfaceDark,
    primary       = MovieColors.Primary,
    textPrimary   = MovieColors.TextPrimaryDark,
    textSecondary = MovieColors.TextSecondaryDark,
    accent        = MovieColors.Accent,
    rarityGold    = MovieColors.RarityGold,
    divider       = MovieColors.DividerDark
)

private val LightColorScheme = MovieColorScheme(
    background    = MovieColors.BackgroundLight,
    card          = MovieColors.CardBackgroundLight,
    surface       = MovieColors.SurfaceLight,
    primary       = MovieColors.Primary,
    textPrimary   = MovieColors.TextPrimaryLight,
    textSecondary = MovieColors.TextSecondaryLight,
    accent        = MovieColors.Accent,
    rarityGold    = MovieColors.RarityGold,
    divider       = MovieColors.DividerLight
)

val LocalMovieColors = staticCompositionLocalOf { DarkColorScheme }

object MovieTheme {
    val colors: MovieColorScheme
        @Composable get() = LocalMovieColors.current
}

@Composable
fun MovieTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    val targetColorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    CompositionLocalProvider(
        LocalMovieColors provides targetColorScheme
    ) {
        content()
    }
}