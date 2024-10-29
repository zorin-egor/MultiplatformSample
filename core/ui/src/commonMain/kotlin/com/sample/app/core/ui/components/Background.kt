package com.sample.app.core.ui.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.sample.app.core.ui.theme.AppTheme
import com.sample.app.core.ui.theme.LocalBackgroundTheme
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
fun AppBackground(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    val color = LocalBackgroundTheme.current.color
    Surface(
        color = if (color == Color.Unspecified) Color.Transparent else color,
        modifier = modifier.fillMaxSize(),
        content =  content
    )
}


@Preview
@Composable
fun BackgroundDefault() {
    AppTheme {
        AppBackground(Modifier.size(100.dp), content = {})
    }
}

@Preview
@Composable
fun BackgroundDynamic() {
    AppTheme {
        AppBackground(Modifier.size(100.dp), content = {})
    }
}

@Preview
@Composable
fun BackgroundAndroid() {
    AppTheme {
        AppBackground(Modifier.size(100.dp), content = {})
    }
}
