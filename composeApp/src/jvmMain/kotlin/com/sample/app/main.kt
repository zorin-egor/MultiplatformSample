package com.sample.app

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Tray
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberNotification
import androidx.compose.ui.window.rememberTrayState
import androidx.compose.ui.window.rememberWindowState
import com.sample.app.core.model.AppConfigModel

fun main() = application {
    PlatformProvider.init(configuration = AppConfigModel())

    val trayState = rememberTrayState()
    val notificaition = rememberNotification("Notification", "Hello, Playzone")

    val state = rememberWindowState(
        placement = WindowPlacement.Floating,
        size = DpSize(1024.dp, 800.dp),
        position = WindowPosition.Aligned(Alignment.Center)
    )

    Tray(
        state = trayState,
        icon = rememberVectorPainter(Icons.Default.Search),
        menu = {
            Item(
                "Push",
                onClick = {
                    trayState.sendNotification(notification = notificaition)
                }
            )

            Item(
                "Exit",
                onClick = {
                    exitApplication()
                }
            )
        }
    )

    Window(
        onCloseRequest = ::exitApplication,
        title = "Multiplatform sample",
        state = state
    ) {
        AppRoot()
    }
}