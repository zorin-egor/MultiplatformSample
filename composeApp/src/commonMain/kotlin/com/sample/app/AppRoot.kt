package com.sample.app

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun AppRoot() {
    MaterialTheme {
        AppScreen()
    }
}