package com.thatwaz.fetchlist.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

val Shapes = Shapes(
    small = RoundedCornerShape(4.dp),
    medium = RoundedCornerShape(8.dp),
    large = RoundedCornerShape(12.dp)
)

// 🎨 Fetch Rewards-Inspired Color Palette

// Light Mode Colors (Brighter, More Fetch-Like)
private val LightColors = lightColorScheme(
    primary = Color(0xFFFFC107), // Fetch Gold/Yellow
    onPrimary = Color.Black,
    secondary = Color(0xFF512DA8), // Deep Purple/Blue
    onSecondary = Color.White,
    background = Color(0xFFFFFDE7), // Soft Creamy White (for warmth)
    surface = Color.White,
    onSurface = Color(0xFF424242), // Dark gray text
)

// Dark Mode Colors (Still Fetch-Inspired, but darker)
private val DarkColors = darkColorScheme(
    primary = Color(0xFFFFD54F), // Lighter Gold for contrast
    onPrimary = Color.Black,
    secondary = Color(0xFF9575CD), // Softer Purple
    onSecondary = Color.Black,
    background = Color(0xFF303030), // Dark Gray
    surface = Color(0xFF424242), // Medium Gray
    onSurface = Color.White
)

// Apply the colors
@Composable
fun FetchListTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColors else LightColors

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}
