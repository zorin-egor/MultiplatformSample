package com.sample.app

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.sample.app.core.ui.components.AppBackground
import com.sample.app.feature.users.navigation.USERS_ROUTE
import com.sample.app.navigation.AppNavHost
import com.sample.app.navigation.NavHostScreens


@Composable
fun AppScreen(
    navController: NavHostController = rememberNavController()
) {
    AppBackground {
        val appState = rememberAppState()
        val backStackEntry by navController.currentBackStackEntryAsState()
        val currentScreen = NavHostScreens.valueOf(
            backStackEntry?.destination?.route ?: NavHostScreens.Users.name
        )

        val snackbarHostState = remember { SnackbarHostState() }
        val snackbarAction: suspend (String, String?) -> Boolean = remember {{ message, action ->
            val isActionPerformed = snackbarHostState.showSnackbar(
                message = message,
                actionLabel = action,
                duration = SnackbarDuration.Short,
            ) == SnackbarResult.ActionPerformed

            if (isActionPerformed) {
                println("Snackbar - action")
            }

            isActionPerformed
        }}

        Scaffold(
            contentColor = MaterialTheme.colors.onBackground,
            contentWindowInsets = WindowInsets.safeDrawing,
            snackbarHost = { SnackbarHost(snackbarHostState) },
            modifier = Modifier.fillMaxSize().imePadding()
        ) { innerPadding ->

            AppNavHost(
                startDestination = USERS_ROUTE,
                appState = appState,
                onShowSnackbar = snackbarAction,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            )
        }
    }
}