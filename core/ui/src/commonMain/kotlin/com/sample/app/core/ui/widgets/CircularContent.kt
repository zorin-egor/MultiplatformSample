package com.sample.app.core.ui.widgets

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CircularContent() {
    Box(modifier = Modifier.fillMaxSize()) {
        CircularProgressIndicator(modifier = Modifier.size(50.dp).align(Alignment.Center))
    }
}