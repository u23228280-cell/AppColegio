package com.example.appcolegio.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val AzulColegio = Color(0xFF0000FF)

private val Esquema = lightColorScheme(
    primary = Color(0xFF6750A4),
    background = Color(0xFFFFFBFE)
)

@Composable
fun AppColegioTheme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = Esquema, content = content)
}
