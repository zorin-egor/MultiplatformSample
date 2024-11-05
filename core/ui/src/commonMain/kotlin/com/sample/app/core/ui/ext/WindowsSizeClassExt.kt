package com.sample.app.core.ui.ext

import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp


expect val screenSize: IntSize @Composable get

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
val windowSizeClass: WindowSizeClass @Composable get() = with(screenSize) {
    val density = LocalDensity.current.density
    val h = (height / density).dp
    val w = (width / density).dp
    println("WindowsSizeClass - height, width: $h, $w}")
    return WindowSizeClass.calculateFromSize(DpSize(width = w, height = h))
}
