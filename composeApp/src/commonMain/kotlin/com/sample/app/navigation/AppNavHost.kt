package com.sample.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import com.sample.app.AppState
import com.sample.app.core.ui.navigation.BackPressHandler
import com.sample.app.feature.repository_details.navigation.navigateToRepositoryDetails
import com.sample.app.feature.repository_details.navigation.repositoriesScreen
import com.sample.app.feature.repository_details.navigation.repositoryDetailsScreen
import com.sample.app.feature.splash.navigation.splashScreen
import com.sample.app.feature.user_details.navigation.navigateToUserDetails
import com.sample.app.feature.user_details.navigation.userDetailsScreen
import com.sample.app.feature.users.navigation.navigateToUsers
import com.sample.app.feature.users.navigation.usersScreen
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
    val shouldShowNavRail = appState.shouldShowNavRail

    BackPressHandler {
        navController.popBackStack()
    }

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
            shouldShowNavRail = shouldShowNavRail,
            navController = navController,
            onShowSnackbar = onShowSnackbar
        )

        repositoriesDetailsPaneScreen(
            appState = appState,
            shouldShowNavRail = shouldShowNavRail,
            navController = navController,
            onShowSnackbar = onShowSnackbar
        )
    }
}

private fun NavGraphBuilder.usersDetailsPaneScreen(
    appState: AppState,
    shouldShowNavRail: Boolean,
    navController: NavController,
    onShowSnackbar: suspend (String, String?) -> Boolean
) {
    if (shouldShowNavRail) {
        usersListDetailsScreen(
            appState = appState,
            onShowSnackbar = onShowSnackbar
        )
    } else {
        usersScreen(
            onUserClick = { id, url ->
                navController.navigateToUserDetails(userId = id, userUrl = url)
            },
            onShowSnackbar = onShowSnackbar
        )
        userDetailsScreen(
            isTopBarVisible = true,
            onShowSnackbar = onShowSnackbar,
            onBackClick = { navController.popBackStack() }
        )
    }
}

private fun NavGraphBuilder.repositoriesDetailsPaneScreen(
    appState: AppState,
    shouldShowNavRail: Boolean,
    navController: NavController,
    onShowSnackbar: suspend (String, String?) -> Boolean,
) {
    if (shouldShowNavRail) {
        reposListDetailsScreen(
            appState = appState,
            onShowSnackbar = onShowSnackbar
        )
    } else {
        repositoriesScreen(
            onRepositoryClick = { owner, repo ->
                navController.navigateToRepositoryDetails(owner = owner, repo = repo)
            },
            onShowSnackbar = onShowSnackbar
        )
        repositoryDetailsScreen(
            isTopBarVisible = true,
            onBackClick = navController::navigateUp,
            onUrlClick = {
                println("userDetailsScreen($it)")
            },
            onShowSnackbar = onShowSnackbar
        )
    }
}