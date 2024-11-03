package com.sample.app.core.ui.ext

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.IntSize

actual val screenSize: IntSize
    @Composable get() = with(LocalContext.current.resources.displayMetrics) {
        IntSize(width = widthPixels, height = heightPixels)
    }