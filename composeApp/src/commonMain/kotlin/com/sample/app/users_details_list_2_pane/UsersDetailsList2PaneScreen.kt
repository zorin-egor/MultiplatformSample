package com.sample.app.users_details_list_2_pane

import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.layout.PaneAdaptedValue
import androidx.compose.material3.adaptive.layout.PaneScaffoldDirective
import androidx.compose.material3.adaptive.navigation.ThreePaneScaffoldNavigator
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sample.app.feature.details.navigation.navigateToUserDetails
import com.sample.app.feature.users.navigation.USERS_ROUTE

private const val USER_DETAILS_PANE_ROUTE = "user_details_pane_route"

fun NavGraphBuilder.usersListDetailsScreen(
    onShowSnackbar: suspend (String, String?) -> Boolean,
) {
    composable(route = USERS_ROUTE) {
        UsersListScreen(
            onShowSnackbar = onShowSnackbar
        )
    }
}

@Composable
internal fun UsersListScreen(
    onShowSnackbar: suspend (String, String?) -> Boolean,
    viewModel: UsersDetailsList2PaneViewModel = viewModel { UsersDetailsList2PaneViewModel() },
) {
    val selectedUser by viewModel.selectedUser.collectAsStateWithLifecycle()
    UsersListScreen(
        selectedUserId = selectedUser?.first,
        selectedUserUrl = selectedUser?.second,
        onUserClick = viewModel::onUserClick,
        onShowSnackbar = onShowSnackbar
    )
}

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
internal fun UsersListScreen(
    selectedUserId: Long?,
    selectedUserUrl: String?,
    onUserClick: (Long, String) -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
) {
    val listDetailNavigator = rememberListDetailPaneScaffoldNavigator<Nothing>(
        scaffoldDirective = PaneScaffoldDirective(
            maxHorizontalPartitions = 1,
            horizontalPartitionSpacerSize = 0.dp,
            maxVerticalPartitions = 1,
            verticalPartitionSpacerSize = 500.dp,
            defaultPanePreferredWidth = 360.dp,
            excludedBounds = emptyList()
        ),

    )
//    BackHandler(listDetailNavigator.canNavigateBack()) {
//        listDetailNavigator.navigateBack()
//    }


    val nestedNavController = rememberNavController()
    val onUrlClick: (String) -> Unit = remember {{ println("detailPane() - userDetailsScreen: $it") }}

    fun onUserClickShowDetailPane(userId: Long, userUrl: String) {
        println("onUserClickShowDetailPane($userId, $userUrl)")
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
//            UsersScreen(
//                onUserClick = ::onUserClickShowDetailPane,
//                onShowSnackbar = onShowSnackbar,
//            )
        },
        detailPane = {
//            Column(Modifier.fillMaxSize()) {
//                NavHost(
//                    navController = nestedNavController,
//                    startDestination = USER_DETAILS_ROUTE,
//                    route = USER_DETAILS_PANE_ROUTE,
//                ) {
//                    composable(route = USER_DETAILS_ROUTE) {
//                        RoundedPlaceholderWidget(
//                            header = stringResource(Res.string.app_common_empty_header),
//                            image = AppIcons.Settings,
//                            modifier = Modifier.padding(all = 8.dp)
//                        )
//                    }
//                    userDetailsScreen(
//                        onShowSnackbar = onShowSnackbar,
//                    )
//                }
//            }
//
        },
    )

    LaunchedEffect(Unit) {
        if (selectedUserId != null && selectedUserUrl != null) {
            onUserClickShowDetailPane(selectedUserId, selectedUserUrl)
        }
    }
}

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
private fun <T> ThreePaneScaffoldNavigator<T>.isListPaneVisible(): Boolean =
    scaffoldValue[ListDetailPaneScaffoldRole.List] == PaneAdaptedValue.Expanded

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
private fun <T> ThreePaneScaffoldNavigator<T>.isDetailPaneVisible(): Boolean =
    scaffoldValue[ListDetailPaneScaffoldRole.Detail] == PaneAdaptedValue.Expanded
