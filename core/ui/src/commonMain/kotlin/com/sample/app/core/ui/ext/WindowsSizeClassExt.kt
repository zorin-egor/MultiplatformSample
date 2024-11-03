package com.sample.app.core.ui.ext

import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp


expect val screenSize: IntSize

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
val windowSizeClass: WindowSizeClass
    @Composable get() = with(screenSize) {
        val density = LocalDensity.current
        val height = (height / density.density).dp
        val width = (width / density.density).dp
        WindowSizeClass.calculateFromSize(DpSize(width = width, height = height))
    }
