package com.sample.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import com.sample.app.AppState
import com.sample.app.feature.splash.navigation.splashScreen
import com.sample.app.feature.user_details.navigation.navigateToUserDetails
import com.sample.app.feature.user_details.navigation.userDetailsScreen
import com.sample.app.feature.users.navigation.navigateToUsers
import com.sample.app.feature.users.navigation.usersScreen


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

        usersDetailsPaneScreen(
            appState = appState,
            navController = navController,
            onShowSnackbar = onShowSnackbar
        )

        repositoriesDetailsPaneScreen(
            appState = appState,
            navController = navController,
            onShowSnackbar = onShowSnackbar
        )
    }
}

private fun NavGraphBuilder.usersDetailsPaneScreen(
    appState: AppState,
    navController: NavController,
    onShowSnackbar: suspend (String, String?) -> Boolean
) {
//    if (true || appState.shouldShowNavRail) {
//        usersListDetailsScreen(
//            onShowSnackbar = onShowSnackbar
//        )
//    } else {
        usersScreen(
            onUserClick = { id, url ->
                navController.navigateToUserDetails(userId = id, userUrl = url)
            },
            onShowSnackbar = onShowSnackbar
        )
        userDetailsScreen(
            onShowSnackbar = onShowSnackbar,
            onBackClick = {
                navController.popBackStack()
            }
        )
//    }
}

private fun NavGraphBuilder.repositoriesDetailsPaneScreen(
    appState: AppState,
    navController: NavController,
    onShowSnackbar: suspend (String, String?) -> Boolean,
) {

}
