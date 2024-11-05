package com.sample.app

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.SnackbarResult
import androidx.compose.material.Text
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import com.sample.app.core.ui.components.AppBackground
import com.sample.app.core.ui.components.AppNavigationBar
import com.sample.app.core.ui.components.AppNavigationBarItem
import com.sample.app.core.ui.components.AppNavigationRail
import com.sample.app.core.ui.components.AppNavigationRailItem
import com.sample.app.navigation.AppNavHost
import com.sample.app.navigation.TopLevelDestination
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toImmutableSet
import org.jetbrains.compose.resources.stringResource


@Composable
fun AppScreen() {

    AppBackground {
        val appState = rememberAppState()
        val snackbarHostState = remember { SnackbarHostState() }
        val snackbarAction: suspend (String, String?) -> Boolean = remember {{ message, action ->
            val isActionPerformed = snackbarHostState.showSnackbar(
                message = message,
                actionLabel = action,
                duration = SnackbarDuration.Short,
            ) == SnackbarResult.ActionPerformed

            if (isActionPerformed) {
                println("AppScreen() - Snackbar - action")
            }

            isActionPerformed
        }}

        Scaffold(
            contentColor = MaterialTheme.colors.onBackground,
            contentWindowInsets = WindowInsets.safeDrawing,
            snackbarHost = { SnackbarHost(snackbarHostState) },
            modifier = Modifier.fillMaxSize().imePadding(),
            bottomBar = {
                if (appState.shouldShowBottomBar) {
                    AppBottomBar(
                        destinations = appState.topLevelDestinations.toImmutableList(),
                        onNavigateToDestination = appState::navigateToTopLevelDestination,
                        currentDestination = appState.currentDestination,
                        modifier = Modifier.testTag("AppBottomBar"),
                    )
                }
            },
        ) { innerPadding ->

            Row(
                Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .consumeWindowInsets(innerPadding)
                    .windowInsetsPadding(
                        WindowInsets.safeDrawing.only(
                            WindowInsetsSides.Horizontal,
                        ),
                    ),
            ) {

                if (appState.shouldShowNavRail && !appState.splashState.value) {
                    AppNavRail(
                        destinations = appState.topLevelDestinations.toImmutableSet(),
                        destinationsWithUnreadResources = emptySet(),
                        onNavigateToDestination = appState::navigateToTopLevelDestination,
                        currentDestination = appState.currentDestination,
                        modifier = Modifier
                            .testTag("AppNavRail")
                            .safeDrawingPadding(),
                    )
                }

                Column(Modifier.fillMaxSize()) {
                    NavAppTopBar(state = appState)
                    AppNavHost(
                        appState = appState,
                        onShowSnackbar = snackbarAction,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}

@Composable
private fun AppBottomBar(
    destinations: ImmutableList<TopLevelDestination>,
    onNavigateToDestination: (TopLevelDestination) -> Unit,
    currentDestination: NavDestination?,
    modifier: Modifier = Modifier,
) {
    AppNavigationBar(
        modifier = modifier,
    ) {
        destinations.forEach { destination ->
            val selected = currentDestination.isTopLevelDestinationInHierarchy(destination)
            AppNavigationBarItem(
                selected = selected,
                onClick = { onNavigateToDestination(destination) },
                icon = {
                    Icon(
                        imageVector = destination.unselectedIcon,
                        contentDescription = null,
                    )
                },
                selectedIcon = {
                    Icon(
                        imageVector = destination.selectedIcon,
                        contentDescription = null,
                    )
                },
                label = { Text(stringResource(destination.iconText)) },
                modifier = modifier,
            )
        }
    }
}

@Composable
private fun AppNavRail(
    destinations: ImmutableSet<TopLevelDestination>,
    destinationsWithUnreadResources: Set<TopLevelDestination>,
    onNavigateToDestination: (TopLevelDestination) -> Unit,
    currentDestination: NavDestination?,
    modifier: Modifier = Modifier,
) {
    AppNavigationRail(modifier = modifier) {
        destinations.forEach { destination ->
            val selected = currentDestination.isTopLevelDestinationInHierarchy(destination)
            val hasUnread = destinationsWithUnreadResources.contains(destination)
            AppNavigationRailItem(
                selected = selected,
                onClick = { onNavigateToDestination(destination) },
                icon = {
                    Icon(
                        imageVector = destination.unselectedIcon,
                        contentDescription = null,
                    )
                },
                selectedIcon = {
                    Icon(
                        imageVector = destination.selectedIcon,
                        contentDescription = null,
                    )
                },
                label = { Text(stringResource(destination.iconText)) },
                modifier = modifier,
            )
        }
    }
}

private fun NavDestination?.isTopLevelDestinationInHierarchy(destination: TopLevelDestination) =
    this?.hierarchy
        ?.any { destination.route.equals(other = it.route, ignoreCase = true) }
        ?: false