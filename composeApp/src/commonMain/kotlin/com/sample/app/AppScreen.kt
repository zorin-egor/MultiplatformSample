package com.sample.app

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.SnackbarResult
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.sample.app.core.ui.components.AppBackground
import com.sample.app.core.ui.components.AppNavigationBar
import com.sample.app.core.ui.components.AppNavigationBarItem
import com.sample.app.core.ui.components.AppNavigationRail
import com.sample.app.core.ui.components.AppNavigationRailItem
import com.sample.app.core.ui.components.AppTopBar
import com.sample.app.core.ui.icon.AppIcons
import com.sample.app.core.ui.resources.core_common_settings
import com.sample.app.core.ui.resources.core_common_share
import com.sample.app.core.ui.viewmodels.TopBarNavigationState
import com.sample.app.core.ui.viewmodels.TopBarNavigationViewModel
import com.sample.app.feature.repository_details.navigation.REPOSITORY_DETAILS_ROUTE_PATH
import com.sample.app.feature.repository_details.resources.feature_repository_details_title
import com.sample.app.feature.user_details.navigation.USER_DETAILS_ROUTE_PATH
import com.sample.app.feature.user_details.resources.feature_user_details_title
import com.sample.app.feature.users.navigation.USERS_ROUTE
import com.sample.app.feature.users.resources.feature_users_title
import com.sample.app.navigation.AppNavHost
import com.sample.app.navigation.TopLevelDestination
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toImmutableSet
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import com.sample.app.core.ui.resources.Res as CoreUiRes
import com.sample.app.feature.repository_details.resources.Res as FeatureRepoDetailsRes
import com.sample.app.feature.user_details.resources.Res as FeatureUserDetailsRes
import com.sample.app.feature.users.resources.Res as FeatureUsersRes


@Composable
fun AppScreen(
    navController: NavHostController = rememberNavController()
) {
    AppBackground {
        val appState = rememberAppState()
        val backStackEntry by navController.currentBackStackEntryAsState()

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

                if (appState.shouldShowNavRail) {
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
                        startDestination = USERS_ROUTE,
                        appState = appState,
                        onShowSnackbar = snackbarAction,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun NavAppTopBar(
    state: AppState,
) {
    val viewModel: TopBarNavigationViewModel = viewModel(LocalViewModelStoreOwner.current!!)

    var isTopBarVisible = false
    var toolbarTitle: StringResource? = null
    var navigationIcon: ImageVector? = null
    var navigationDesc: String? = null
    var actionIcon: ImageVector? = null
    var actionDesc: String? = null
    var actionClick: () -> Unit = {}
    val backClick: () -> Unit = { state.navController.navigateUp() }
    val route = state.currentDestination?.route

    println("NavAppTopBar() - ${state.shouldShowBottomBar}, $route, ${viewModel.hashCode()}")

    when (route) {
        USERS_ROUTE -> {
            toolbarTitle = FeatureUsersRes.string.feature_users_title
            actionIcon = AppIcons.Settings
            actionDesc = stringResource(resource = CoreUiRes.string.core_common_settings)
            actionClick = { viewModel.emit(route, TopBarNavigationState.Menu) }
            isTopBarVisible = true
        }
        USER_DETAILS_ROUTE_PATH -> {
            toolbarTitle = FeatureUserDetailsRes.string.feature_user_details_title
            navigationIcon = AppIcons.ArrowBack
            navigationDesc = stringResource(FeatureUserDetailsRes.string.feature_user_details_title)
            actionIcon = AppIcons.Share
            actionDesc = stringResource(resource = CoreUiRes.string.core_common_share)
            actionClick = { viewModel.emit(route, TopBarNavigationState.Menu) }
            isTopBarVisible = true
        }
        REPOSITORY_DETAILS_ROUTE_PATH -> {
            toolbarTitle = FeatureRepoDetailsRes.string.feature_repository_details_title
            navigationIcon = AppIcons.ArrowBack
            navigationDesc = stringResource(FeatureRepoDetailsRes.string.feature_repository_details_title)
            actionDesc = stringResource(resource = CoreUiRes.string.core_common_share)
            actionClick = { viewModel.emit(route, TopBarNavigationState.Menu) }
            isTopBarVisible = true
        }
    }

    if (!isTopBarVisible) {
        return
    }

    AppTopBar(
        title = toolbarTitle?.let { stringResource(it) },
        navigationIcon = navigationIcon,
        navigationIconContentDescription = navigationDesc,
        actionIcon = actionIcon,
        actionIconContentDescription = actionDesc,
//        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
//            containerColor = Color.Transparent,
//        ),
        onActionClick = actionClick,
        onNavigationClick = backClick,
        modifier = Modifier.fillMaxWidth().wrapContentHeight()
    )
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