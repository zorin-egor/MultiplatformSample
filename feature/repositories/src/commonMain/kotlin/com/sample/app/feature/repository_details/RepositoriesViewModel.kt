package com.sample.app.feature.repository_details

import androidx.lifecycle.viewModelScope
import com.sample.app.core.common.di.Inject
import com.sample.app.core.common.result.Result
import com.sample.app.core.domain.GetRecentSearchUseCase
import com.sample.app.core.domain.GetRepositoriesByNameUseCase
import com.sample.app.core.domain.SetRecentSearchUseCase
import com.sample.app.core.model.RecentSearchModel
import com.sample.app.core.model.RepositoryModel
import com.sample.app.core.model.exceptions.EmptyException
import com.sample.app.core.ui.ext.toStringResource
import com.sample.app.core.ui.viewmodels.StateViewModel
import com.sample.app.core.ui.viewmodels.UiState
import com.sample.app.core.ui.widgets.SearchTextDataItem
import com.sample.app.feature.repository_details.models.RecentSearchUiModel
import com.sample.app.feature.repository_details.models.RepositoriesActions
import com.sample.app.feature.repository_details.models.RepositoriesEvents
import com.sample.app.feature.repository_details.models.RepositoriesUiModel
import com.sample.app.feature.repository_details.models.RepositoriesUiState
import com.sample.app.feature.repository_details.models.emptyRepositoriesUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update


class RepositoriesViewModel(
    private val getReposByNameUseCase: GetRepositoriesByNameUseCase = Inject.instance(),
    private val getRecentSearchUseCase: GetRecentSearchUseCase = Inject.instance(),
    private val setRecentSearchUseCase: SetRecentSearchUseCase = Inject.instance(),
) : StateViewModel<RepositoriesUiState, RepositoriesActions, RepositoriesEvents>(
    initialAction = RepositoriesActions.None
) {

    private val _searchQuery = MutableStateFlow("")

    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _nextRepositories = MutableStateFlow(true)

    private val _nextRepositoriesFlow = _nextRepositories.filter { it }

    override val state: StateFlow<RepositoriesUiState> =
        _searchQuery.combine(_nextRepositoriesFlow) { search, next ->
            println("combine($search, $next) - trigger")
            _nextRepositories.update { false }
            search
        }.flatMapLatest { searchQuery ->
            println("combine($searchQuery) - flatMapLatest")

            val searchFlow = getRecentSearchUseCase(query = searchQuery)
                .mapNotNull { mapTo(item = it, query = searchQuery, lastItemCacheState?.searchState) }
                .catch { println("Search flow\n$it") }

            val repoFlow = getReposByNameUseCase(name = searchQuery)
                .onStart { delay(500) }
                .mapNotNull { mapTo(item = it, previousState = lastItemCacheState?.repoState) }
                .catch {
                    println("Repo flow\n$it")
                    setAction(RepositoriesActions.ShowError(it.toStringResource))
                }

            repoFlow.combine(searchFlow) { repoState, searchState ->
                println("combine(${repoState::class.simpleName}, ${searchState::class.simpleName}) - merge")
                RepositoriesUiState(
                    searchState = searchState,
                    repoState = repoState
                )
            }
    }.catch { error ->
        println("catch\n$error")
        setAction(RepositoriesActions.ShowError(error.toStringResource))
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = emptyRepositoriesUiState
    )

    override fun setEvent(item: RepositoriesEvents) {
        println("setEvents($item)")
        when(item) {
            RepositoriesEvents.None -> setAction(RepositoriesActions.None)
            RepositoriesEvents.NextRepositories -> _nextRepositories.tryEmit(true)
            is RepositoriesEvents.SearchQuery -> _searchQuery.tryEmit(item.query)
            is RepositoriesEvents.OnRepositoryClick -> setAction(
                RepositoriesActions.NavigationToDetails(
                owner = item.item.owner,
                name = item.item.name
            ))
        }
    }

    private fun mapTo(item: Result<List<RecentSearchModel>>, query: String, previousState: UiState<RecentSearchUiModel>?): UiState<RecentSearchUiModel>? {
        return when(item) {
            Result.Loading -> UiState.Success(RecentSearchUiModel(
                query = query,
                recentSearch = emptyList()
            ))

            is Result.Error -> {
                println("message = search mapTo\n${item.exception}")
                previousState
            }

            is Result.Success -> {
                setRecentSearchUseCase(query)
                UiState.Success(RecentSearchUiModel(
                    query = query,
                    recentSearch = mapTo(item.data)
                ))
            }
        }
    }

    private fun mapTo(item: Result<List<RepositoryModel>>, previousState: UiState<RepositoriesUiModel>?): UiState<RepositoriesUiModel>? {
        val successState = previousState as? UiState.Success

        return when(item) {
            Result.Loading -> successState?.item?.copy(isBottomProgress = true)
                ?.let { UiState.Success(it) }
                ?: UiState.Loading

            is Result.Error -> {
                setAction(RepositoriesActions.ShowError(item.exception.toStringResource))
                when {
                    successState != null -> successState.copy(item = successState.item.copy(isBottomProgress = false))
                    item.exception is EmptyException -> UiState.Empty
                    else -> UiState.Error(item.exception)
                }
            }

            is Result.Success -> {
                if (item.data.isNotEmpty()) {
                    UiState.Success(
                        RepositoriesUiModel(
                        repositories = item.data,
                        isBottomProgress = false
                    )
                    )
                } else {
                    UiState.Empty
                }
            }
        }
    }

    private fun mapTo(item: RecentSearchModel): SearchTextDataItem =
        SearchTextDataItem(id = item.tag.name, text = item.query)

    private fun mapTo(item: List<RecentSearchModel>): List<SearchTextDataItem> =
        item.map(::mapTo)

}