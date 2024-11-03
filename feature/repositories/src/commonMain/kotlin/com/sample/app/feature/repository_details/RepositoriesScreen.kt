package com.sample.app.feature.repository_details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sample.app.core.ui.icon.AppIcons
import com.sample.app.core.ui.resources.core_common_empty_placeholder_header
import com.sample.app.core.ui.resources.core_common_empty_placeholder_title
import com.sample.app.core.ui.resources.core_common_search_placeholder_header
import com.sample.app.core.ui.resources.core_common_search_placeholder_title
import com.sample.app.core.ui.viewmodels.UiState
import com.sample.app.core.ui.widgets.CircularContent
import com.sample.app.core.ui.widgets.ExposedTextField
import com.sample.app.core.ui.widgets.ListContentWidget
import com.sample.app.core.ui.widgets.SimplePlaceholderContent
import com.sample.app.feature.repositories.resources.Res
import com.sample.app.feature.repositories.resources.feature_repositories_by_name_search_title
import com.sample.app.feature.repository_details.models.RepositoriesActions
import com.sample.app.feature.repository_details.models.RepositoriesEvents
import com.sample.app.feature.repository_details.widgets.RepositoriesItemContent
import org.jetbrains.compose.resources.stringResource
import com.sample.app.core.ui.resources.Res as CoreUiRes


@Composable
fun RepositoriesScreen(
    onRepositoryClick: (String, String) -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    modifier: Modifier = Modifier,
    onSearchClear: (() -> Unit)? = null,
    viewModel: RepositoriesViewModel = viewModel { RepositoriesViewModel() },
) {
    println("RepositoriesScreen()")

    val onNextRepositories = remember {{ viewModel.setEvent(RepositoriesEvents.NextRepositories) }}
    val reposUiState by viewModel.state.collectAsStateWithLifecycle()
    val reposAction by viewModel.action.collectAsStateWithLifecycle()

    when(val action = reposAction) {
        is RepositoriesActions.NavigationToDetails -> {
            onRepositoryClick(action.owner, action.name)
            viewModel.setEvent(RepositoriesEvents.None)
        }
        is RepositoriesActions.ShowError -> {
            val error = stringResource(action.error)
            LaunchedEffect(key1 = action) {
                onShowSnackbar(error, null)
                viewModel.setEvent(RepositoriesEvents.None)
            }
        }
        else -> {}
    }

    println("RepositoriesScreen() - state, action: $reposUiState, $reposAction")

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        val queryState by viewModel.searchQuery.collectAsStateWithLifecycle()
        val searchState = (reposUiState.searchState as? UiState.Success)?.item
        val options = searchState?.recentSearch ?: emptyList()
        val inputTextPlaceholder = stringResource(Res.string.feature_repositories_by_name_search_title)

        ExposedTextField(
            searchQuery = queryState,
            options = options,
            contentDescriptionSearch = inputTextPlaceholder,
            contentDescriptionClose = inputTextPlaceholder,
            onSearchQueryChanged = {
                println("RepositoriesScreen() - onSearchQueryChanged: $it")
                viewModel.setEvent(RepositoriesEvents.SearchQuery(it.text))
                if (it.text.isEmpty()) {
                    onSearchClear?.invoke()
                }
            },
            modifier = Modifier.wrapContentHeight().fillMaxWidth(),
            placeholder = inputTextPlaceholder,
            isFocusRequest = true,
        )

        when(val state = reposUiState.repoState) {
            UiState.Loading -> CircularContent()
            UiState.Empty -> SimplePlaceholderContent(
                header = stringResource(CoreUiRes.string.core_common_empty_placeholder_header),
                title = stringResource(CoreUiRes.string.core_common_empty_placeholder_title),
                image = AppIcons.Empty,
                imageContentDescription = stringResource(CoreUiRes.string.core_common_empty_placeholder_title)
            )
            is UiState.Error -> SimplePlaceholderContent(
                header = stringResource(CoreUiRes.string.core_common_search_placeholder_header),
                title = stringResource(CoreUiRes.string.core_common_search_placeholder_title),
                image = AppIcons.Search,
                imageContentDescription = stringResource(CoreUiRes.string.core_common_search_placeholder_title)
            )
            is UiState.Success -> {
                ListContentWidget(
                    items = state.item.repositories,
                    onKey = { it.id.toString() },
                    onBottomEvent = onNextRepositories,
                    isBottomProgress = state.item.isBottomProgress,
                ) { repository ->
                    RepositoriesItemContent(
                        repository = repository,
                        onEventAction = viewModel::setEvent,
                        modifier = Modifier.fillMaxWidth()
                            .height(110.dp)
                    )
                }
            }
        }
    }

}

