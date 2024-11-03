package com.sample.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.sample.app.AppState
import com.sample.app.feature.splash.navigation.splashScreen
import com.sample.app.feature.users.navigation.navigateToUsers
import com.sample.app.panes.repos_details_list_2_pane.reposListDetailsScreen
import com.sample.app.panes.users_details_list_2_pane.usersListDetailsScreen


@Composable
fun AppNavHost(
    startDestination: String,
    appState: AppState,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    modifier: Modifier = Modifier,
) {
    println("NavHost($appState)")

    val navController = appState.navController

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        splashScreen(
            onShowSnackbar = onShowSnackbar,
            onNavigationEvent = {
                navController.navigateToUsers()
            }
        )

        usersListDetailsScreen(
            onShowSnackbar = onShowSnackbar
        )

        reposListDetailsScreen(
            onShowSnackbar = onShowSnackbar
        )
    }
}