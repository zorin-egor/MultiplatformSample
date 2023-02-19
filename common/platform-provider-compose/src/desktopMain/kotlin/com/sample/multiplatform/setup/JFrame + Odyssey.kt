package com.sample.multiplatform.setup

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.awt.ComposePanel
import com.sample.multiplatform.navigation.NavigationSource
import com.sample.multiplatform.navigation.NavigationTree
import com.sample.multiplatform.navigation.generateGraph
import com.sample.multiplatform.theme.AppTheme
import com.sample.multiplatform.theme.Theme
import ru.alexgladkov.odyssey.compose.base.Navigator
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import ru.alexgladkov.odyssey.compose.navigation.RootComposeBuilder
import ru.alexgladkov.odyssey.compose.navigation.modal_navigation.ModalNavigator
import ru.alexgladkov.odyssey.compose.navigation.modal_navigation.configuration.DefaultModalConfiguration
import ru.alexgladkov.odyssey.core.configuration.DisplayType
import java.awt.BorderLayout
import javax.swing.JFrame
import javax.swing.WindowConstants

fun JFrame.setupThemedNavigation() {
    val rootController = RootComposeBuilder().apply {
        generateGraph(NavigationSource.Desktop)
    }.build()

    defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
    title = "App github"

    val composePanel = ComposePanel()
    composePanel.setContent {
        CompositionLocalProvider(
            LocalRootController provides rootController
        ) {
            AppTheme {
                val backgroundColor = Theme.colors.primaryBackground
                rootController.backgroundColor = backgroundColor

                ModalNavigator(DefaultModalConfiguration(
                    backgroundColor = backgroundColor,
                    displayType = DisplayType.FullScreen
                )) {
                    Navigator(startScreen = NavigationTree.Users.UsersScreen.name)
                }
            }
        }
    }

    contentPane.add(composePanel, BorderLayout.CENTER)
    setSize(800, 600)
    setLocationRelativeTo(null)
    isVisible = true
}