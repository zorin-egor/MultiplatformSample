package com.sample.app.core.ui.ext

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.IntSize

@OptIn(ExperimentalComposeUiApi::class)
actual val screenSize: IntSize
    @Composable get() = LocalWindowInfo.current.containerSize