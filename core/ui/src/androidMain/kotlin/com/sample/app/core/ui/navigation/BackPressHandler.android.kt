package com.sample.app.core.ui.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable

@Composable
actual fun BackPressHandler(enabled: Boolean, onBackEvent: () -> Unit) {
    BackHandler(enabled = enabled, onBack = onBackEvent)
}