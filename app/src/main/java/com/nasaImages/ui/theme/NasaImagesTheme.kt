package com.nasaImages.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val LightColorPalette = lightColors()

private val DarkColorPalette = darkColors()

@Composable
fun NasaImagesTheme(content: @Composable () -> Unit) {
    MaterialTheme(content = content)
}
