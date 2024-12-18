package com.sample.app.panes.users_details_list_2_pane

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sample.app.AppState
import com.sample.app.UserDetailsTopAppBar
import com.sample.app.UsersTopAppBar
import com.sample.app.core.ui.icon.AppIcons
import com.sample.app.core.ui.navigation.BackPressHandler
import com.sample.app.core.ui.widgets.RoundedPlaceholderWidget
import com.sample.app.feature.user_details.navigation.USER_DETAILS_ROUTE
import com.sample.app.feature.user_details.navigation.navigateToUserDetails
import com.sample.app.feature.user_details.navigation.userDetailsScreen
import com.sample.app.feature.users.UsersScreen
import com.sample.app.feature.users.navigation.USERS_ROUTE
import multiplatformsample.composeapp.generated.resources.Res
import multiplatformsample.composeapp.generated.resources.app_common_empty_header
import org.jetbrains.compose.resources.stringResource

private const val USER_DETAILS_PANE_ROUTE = "user_details_pane_route"

fun NavGraphBuilder.usersListDetailsScreen(
    appState: AppState,
    onShowSnackbar: suspend (String, String?) -> Boolean,
) {
    composable(route = USERS_ROUTE) {
        UsersListScreen(
            appState = appState,
            onUserClick = { id, url -> println("usersListDetailsScreen() - id, url: $id, $url") },
            onShowSnackbar = onShowSnackbar
        )
    }
}

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
internal fun UsersListScreen(
    appState: AppState,
    onUserClick: (Long, String) -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
) {
    val listDetailNavigator = rememberListDetailPaneScaffoldNavigator<Nothing>()
    val backAction: () -> Unit = remember {{ listDetailNavigator.navigateBack() }}
    BackPressHandler(enabled = listDetailNavigator.canNavigateBack(), onBackEvent = backAction)

    val nestedNavController = rememberNavController()
    val onUrlClick: (String) -> Unit = remember {{ println("detailPane() - userDetailsScreen: $it") }}

    fun onUserClickShowDetailPane(userId: Long, userUrl: String) {
        println("UsersListScreen() - onUserClickShowDetailPane($userId, $userUrl)")
        onUserClick(userId, userUrl)
        nestedNavController.navigateToUserDetails(userId = userId, userUrl = userUrl) {
            popUpTo(USER_DETAILS_PANE_ROUTE)
        }
        listDetailNavigator.navigateTo(ListDetailPaneScaffoldRole.Detail)
    }

    ListDetailPaneScaffold(
        value = listDetailNavigator.scaffoldValue,
        directive = listDetailNavigator.scaffoldDirective,
        listPane = {
            Column(modifier = Modifier.fillMaxSize()) {
                if (appState.shouldShowBottomBar) {
                    UsersTopAppBar(onBackClick = {})
                }

                UsersScreen(
                    onUserClick = ::onUserClickShowDetailPane,
                    onShowSnackbar = onShowSnackbar,
                )
            }
        },
        detailPane = {
            Column(modifier = Modifier.fillMaxSize()) {
                if (appState.shouldShowBottomBar) {
                    UserDetailsTopAppBar(
                        onBackClick = { listDetailNavigator.navigateBack() }
                    )
                }

                NavHost(
                    navController = nestedNavController,
                    startDestination = USER_DETAILS_ROUTE,
                    route = USER_DETAILS_PANE_ROUTE,
                ) {
                    composable(route = USER_DETAILS_ROUTE) {
                        RoundedPlaceholderWidget(
                            header = stringResource(Res.string.app_common_empty_header),
                            image = AppIcons.Settings,
                            modifier = Modifier.padding(all = 8.dp)
                        )
                    }
                    userDetailsScreen(
                        isTopBarVisible = true,
                        onBackClick = backAction,
                        onShowSnackbar = onShowSnackbar,
                    )
                }
            }
        }
    )
}
