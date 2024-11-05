package com.sample.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import com.sample.app.AppState
import com.sample.app.core.ui.navigation.BackPressHandler
import com.sample.app.feature.splash.navigation.splashScreen
import com.sample.app.feature.users.navigation.navigateToUsers
import com.sample.app.panes.repos_details_list_2_pane.reposListDetailsScreen
import com.sample.app.panes.users_details_list_2_pane.usersListDetailsScreen


@Composable
fun AppNavHost(
    appState: AppState,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    modifier: Modifier = Modifier,
) {
    println("AppNavHost()")

    val navController = appState.navController

    BackPressHandler {
        println("AppNavHost() - navController: ${navController.hashCode()}")
        navController.popBackStack()
    }

    DisposableEffect(navController) {
        val callback = NavController.OnDestinationChangedListener { nav, dest, args ->
            println("AppNavHost() - ${dest.route}, $args")
            appState.routeState.value = dest.route
        }
        navController.addOnDestinationChangedListener(callback)
        onDispose {
            navController.removeOnDestinationChangedListener(callback)
        }
    }

    NavHost(
        navController = navController,
        startDestination = appState.startDestination,
        modifier = modifier,
    ) {
        splashScreen(
            onShowSnackbar = onShowSnackbar,
            onNavigationEvent = {
                navController.navigateToUsers()
            }
        )

        usersListDetailsScreen(
            appState = appState,
            onShowSnackbar = onShowSnackbar
        )

        reposListDetailsScreen(
            appState = appState,
            onShowSnackbar = onShowSnackbar
        )
    }
}