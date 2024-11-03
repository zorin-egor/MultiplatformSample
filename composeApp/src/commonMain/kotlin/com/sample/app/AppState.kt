package com.sample.app

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.sample.app.core.ui.ext.windowSizeClass
import com.sample.app.feature.repository_details.navigation.REPOSITORIES_ROUTE
import com.sample.app.feature.repository_details.navigation.navigateToRepositories
import com.sample.app.feature.users.navigation.USERS_ROUTE
import com.sample.app.feature.users.navigation.navigateToUsers
import com.sample.app.navigation.TopLevelDestination
import kotlinx.coroutines.CoroutineScope

val NavHostController.currentDestinationFromState: NavDestination?
    @Composable get() = currentBackStackEntryAsState().value?.destination

@Composable
fun rememberAppState(
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    navController: NavHostController = rememberNavController(),
): AppState {

    return remember(
        navController,
        coroutineScope,
    ) {
        AppState(
            navController = navController,
            coroutineScope = coroutineScope,
        )
    }
}

@Stable
class AppState(
    val navController: NavHostController,
    coroutineScope: CoroutineScope? = null,
) {

    val currentDestination: NavDestination?
        @Composable get() = navController.currentDestinationFromState

    val currentTopLevelDestination: TopLevelDestination?
        @Composable get() = when (currentDestination?.route) {
            USERS_ROUTE -> TopLevelDestination.USERS
            REPOSITORIES_ROUTE -> TopLevelDestination.REPOSITORIES
            else -> null
        }

    val isAppFocused: Boolean
        @Composable get() = LocalWindowInfo.current.isWindowFocused

    val shouldShowBottomBar: Boolean
        @Composable get() = windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact

    val shouldShowNavRail: Boolean
        @Composable get() = !shouldShowBottomBar

    val topLevelDestinations: List<TopLevelDestination> = TopLevelDestination.entries

    fun navigateToTopLevelDestination(topLevelDestination: TopLevelDestination) {
        val topLevelNavOptions = navOptions {
            popUpTo(navController.graph.findStartDestination().route!!) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }

        when (topLevelDestination) {
            TopLevelDestination.USERS -> navController.navigateToUsers(navOptions = topLevelNavOptions)
            TopLevelDestination.REPOSITORIES -> navController.navigateToRepositories(navOptions = topLevelNavOptions)
        }
    }

}
