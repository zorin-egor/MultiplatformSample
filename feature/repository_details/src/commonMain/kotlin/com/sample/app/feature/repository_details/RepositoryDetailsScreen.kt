package com.sample.app.feature.repository_details

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sample.app.core.ui.viewmodels.TopBarNavigationState
import com.sample.app.core.ui.viewmodels.TopBarNavigationViewModel
import com.sample.app.core.ui.viewmodels.UiState
import com.sample.app.core.ui.widgets.CircularContent
import com.sample.app.feature.repository_details.models.RepositoryDetailsActions
import com.sample.app.feature.repository_details.models.RepositoryDetailsEvents
import com.sample.app.feature.repository_details.navigation.REPOSITORY_DETAILS_ROUTE_PATH
import com.sample.app.feature.repository_details.navigation.RepositoryDetailsArgs
import com.sample.app.feature.repository_details.widgets.RepositoryDetailsContent
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun RepositoryDetailsScreen(
    args: RepositoryDetailsArgs,
    isTopBarVisible: Boolean,
    onUrlClick: (String) -> Unit,
    onBackClick: () -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    modifier: Modifier = Modifier,
    viewModel: RepositoryDetailsViewModel = viewModel { RepositoryDetailsViewModel() },
    topBarViewModel: TopBarNavigationViewModel = viewModel()
) {
    println("RepositoryDetailsScreen()")

    remember { viewModel.setEvent(RepositoryDetailsEvents.GetRepo(owner = args.owner, repo = args.repo)) }

    val detailsState by viewModel.state.collectAsStateWithLifecycle()
    val detailsAction by viewModel.action.collectAsStateWithLifecycle()
    val onShareClick: (String) -> Unit = remember {{ println("RepositoryDetailsScreen() - share link: $it") }}

    val topBarNavigationState = topBarViewModel.collect(key = REPOSITORY_DETAILS_ROUTE_PATH)
        .collectAsStateWithLifecycle(initialValue = TopBarNavigationState.None)

    when(val action = topBarNavigationState.value) {
        is TopBarNavigationState.Menu -> {
            println("TopBarNavigationState: menu")
            viewModel.setEvent(RepositoryDetailsEvents.ShareProfile)
            topBarViewModel.emit(REPOSITORY_DETAILS_ROUTE_PATH, TopBarNavigationState.None)
        }
        is TopBarNavigationState.Back -> {
            viewModel.setEvent(RepositoryDetailsEvents.NavigationBack)
        }
        else -> println("TopBarNavigationState: $action")
    }

    when(val action = detailsAction) {
        is RepositoryDetailsActions.ShareUrl -> {
            onShareClick(action.url)
            viewModel.setEvent(RepositoryDetailsEvents.None)
        }
        is RepositoryDetailsActions.ShowError -> {
            val error = stringResource(action.error)
            LaunchedEffect(key1 = action) {
                onShowSnackbar(error, null)
                viewModel.setEvent(RepositoryDetailsEvents.None)
            }
        }
        else -> {}
    }

    println("RepositoryDetailsScreen() - state: $detailsState, $detailsAction")

    when (val state = detailsState) {
        UiState.Loading -> CircularContent()
        is UiState.Success -> RepositoryDetailsContent(
            isTopBarVisible = isTopBarVisible,
            repositoryDetails = state.item,
            onEventAction = viewModel::setEvent,
            modifier = Modifier.fillMaxSize().padding(8.dp)
        )
        else -> {}
    }
}