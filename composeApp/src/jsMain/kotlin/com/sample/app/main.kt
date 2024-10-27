package com.sample.app

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.CanvasBasedWindow
import com.sample.app.core.model.AppConfigModel
import org.jetbrains.skiko.wasm.onWasmReady

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    onWasmReady {
        CanvasBasedWindow {
            PlatformProvider.init(AppConfigModel())
            App()
        }
    }
}