package com.thatwaz.fetchlist.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
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

private val LightColors = lightColorScheme(
    primary = Color(0xFFFFC107), // Fetch Gold/Yellow
    onPrimary = Color.Black,
    secondary = Color(0xFF512DA8), // Deep Purple/Blue
    onSecondary = Color.White,
    background = Color(0xFFFFFDE7), // Soft Creamy White
    surface = Color.White,
    onSurface = Color(0xFF424242), // Dark gray text
)

// ✅ Always apply light theme
@Composable
fun FetchListTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}
