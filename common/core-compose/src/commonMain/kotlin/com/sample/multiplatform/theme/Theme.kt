package com.sample.multiplatform.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider


@Composable
fun AppTheme(content: @Composable () -> Unit) {
    CompositionLocalProvider(
        LocalColorProvider provides palette,
        content = content
    )
}

object Theme {
    val colors: AppColors
        @Composable
        get() = LocalColorProvider.current
}