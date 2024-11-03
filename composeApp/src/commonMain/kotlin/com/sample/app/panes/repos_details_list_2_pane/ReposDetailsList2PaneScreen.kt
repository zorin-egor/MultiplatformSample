package com.sample.app.panes.repos_details_list_2_pane

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
import com.sample.app.core.ui.icon.AppIcons
import com.sample.app.core.ui.widgets.RoundedPlaceholderWidget
import com.sample.app.feature.repository_details.RepositoriesScreen
import com.sample.app.feature.repository_details.navigation.REPOSITORIES_ROUTE
import com.sample.app.feature.repository_details.navigation.REPOSITORY_DETAILS_ROUTE
import com.sample.app.feature.repository_details.navigation.navigateToRepositoryDetails
import com.sample.app.feature.repository_details.navigation.repositoryDetailsScreen
import multiplatformsample.composeapp.generated.resources.Res
import multiplatformsample.composeapp.generated.resources.app_common_empty_header
import org.jetbrains.compose.resources.stringResource

private const val REPO_DETAILS_PANE_ROUTE = "repo_details_pane_route"

fun NavGraphBuilder.reposListDetailsScreen(
    onShowSnackbar: suspend (String, String?) -> Boolean,
) {
    composable(route = REPOSITORIES_ROUTE) {
        ReposListScreen(
            onRepoClick = { id, owner -> println("reposListDetailsScreen() - id, owner: $id, $owner") },
            onShowSnackbar = onShowSnackbar
        )
    }
}

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
internal fun ReposListScreen(
    onRepoClick: (String, String) -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
) {
    val listDetailNavigator = rememberListDetailPaneScaffoldNavigator<Nothing>()
//    BackHandler(listDetailNavigator.canNavigateBack()) {
//        listDetailNavigator.navigateBack()
//    }

    val nestedNavController = rememberNavController()
    val onUrlClick: (String) -> Unit = remember {{ println("detailPane() - repoDetailsScreen: $it") }}

    fun onRepoClickShowDetailPane(owner: String, repo: String) {
        println("onRepoClickShowDetailPane($owner, $repo)")
        onRepoClick(owner, repo)
        nestedNavController.navigateToRepositoryDetails(owner = owner, repo = repo) {
            popUpTo(REPO_DETAILS_PANE_ROUTE)
        }
        listDetailNavigator.navigateTo(ListDetailPaneScaffoldRole.Detail)
    }

    ListDetailPaneScaffold(
        value = listDetailNavigator.scaffoldValue,
        directive = listDetailNavigator.scaffoldDirective,
        listPane = {
            RepositoriesScreen(
                onRepositoryClick = ::onRepoClickShowDetailPane,
                onShowSnackbar = onShowSnackbar,
            )
        },
        detailPane = {
            Column(Modifier.fillMaxSize()) {
                NavHost(
                    navController = nestedNavController,
                    startDestination = REPOSITORY_DETAILS_ROUTE,
                    route = REPO_DETAILS_PANE_ROUTE,
                ) {
                    composable(route = REPOSITORY_DETAILS_ROUTE) {
                        RoundedPlaceholderWidget(
                            header = stringResource(Res.string.app_common_empty_header),
                            image = AppIcons.Settings,
                            modifier = Modifier.padding(all = 8.dp)
                        )
                    }
                    repositoryDetailsScreen(
                        onBackClick = { nestedNavController.popBackStack() },
                        onShowSnackbar = onShowSnackbar,
                        onUrlClick = onUrlClick
                    )
                }
            }
        },
    )
}
