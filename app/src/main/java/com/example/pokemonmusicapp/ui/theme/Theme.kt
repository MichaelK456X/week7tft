package com.example.pokemonmusicapp.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryGreen,
    background = DarkBackground,
    surface = CardBackground,
    onBackground = TextWhite,
    onSurface = TextWhite
)

@Composable
fun PokemonmusicappTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = Typography,
        content = content
    )
}
