package com.nasaImages.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val LightColorPalette = lightColors(
    background = LightBackground
)

@Composable
fun NasaImagesTheme(content: @Composable () -> Unit) {
    val systemUiController = rememberSystemUiController()

    systemUiController.setSystemBarsColor(
        color = LightBackground
    )

    MaterialTheme(
        colors = LightColorPalette,
        content = content
    )
}
