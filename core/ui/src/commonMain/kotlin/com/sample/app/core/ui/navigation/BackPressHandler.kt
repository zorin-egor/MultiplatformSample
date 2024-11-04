package com.sample.app.core.ui.navigation

import androidx.compose.runtime.Composable

@Composable
expect fun BackPressHandler(enabled: Boolean = true, onBackEvent: () -> Unit)