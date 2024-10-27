package com.sample.app

import androidx.compose.ui.window.ComposeUIViewController
import com.sample.app.core.model.AppConfigModel

fun MainViewController() = ComposeUIViewController {
    PlatformProvider.init(AppConfigModel())
    App()
}