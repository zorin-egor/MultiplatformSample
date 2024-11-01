package com.sample.app.feature.repositories

import androidx.lifecycle.viewModelScope
import com.sample.app.core.common.result.Result
import com.sample.app.core.ui.viewmodels.StateViewModel
import com.sample.app.feature.repositories.models.RecentSearchUiModel
import com.sample.app.feature.repositories.models.RepositoriesActions
import com.sample.app.feature.repositories.models.RepositoriesEvents
import com.sample.app.feature.repositories.models.RepositoriesUiModel
import com.sample.app.feature.repositories.models.RepositoriesUiState
import com.sample.app.feature.repositories.models.emptyRepositoriesUiState
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
    private val getReposByNameUseCase: GetRepositoriesByNameUseCase,
    private val getRecentSearchUseCase: GetRecentSearchUseCase,
    private val setRecentSearchUseCase: SetRecentSearchUseCase,
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
                .catch { println(t = it, message = "Search flow") }

            val repoFlow = getReposByNameUseCase(name = searchQuery)
                .onStart { delay(500) }
                .mapNotNull { mapTo(item = it, previousState = lastItemCacheState?.repoState) }
                .catch {
                    println(t = it, message = "Repo flow")
                    setAction(RepositoriesActions.ShowError(it))
                }

            repoFlow.combine(searchFlow) { repoState, searchState ->
                println("combine(${repoState::class.simpleName}, ${searchState::class.simpleName}) - merge")
                RepositoriesUiState(
                    searchState = searchState,
                    repoState = repoState
                )
            }
    }.catch { error ->
        println(t = error, message = "catch")
        setAction(RepositoriesActions.ShowError(error))
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

    private fun mapTo(item: Result<List<RecentSearch>>, query: String, previousState: UiState<RecentSearchUiModel>?): UiState<RecentSearchUiModel>? {
        return when(item) {
            Result.Loading -> UiState.Success(
                RecentSearchUiModel(
                query = query,
                recentSearch = emptyList()
            )
            )

            is Result.Error -> {
                println(t = item.exception, message = "search mapTo")
                previousState
            }

            is Result.Success -> {
                setRecentSearchUseCase(query)
                UiState.Success(
                    RecentSearchUiModel(
                    query = query,
                    recentSearch = mapTo(item.data)
                )
                )
            }
        }
    }

    private fun mapTo(item: Result<List<Repository>>, previousState: UiState<RepositoriesUiModel>?): UiState<RepositoriesUiModel>? {
        val successState = previousState as? UiState.Success

        return when(item) {
            Result.Loading -> successState?.item?.copy(isBottomProgress = true)
                ?.let { UiState.Success(it) }
                ?: UiState.Loading

            is Result.Error -> {
                setAction(RepositoriesActions.ShowError(item.exception))
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

    private fun mapTo(item: RecentSearch): SearchTextDataItem =
        SearchTextDataItem(id = item.tag.name, text = item.value)

    private fun mapTo(item: List<RecentSearch>): List<SearchTextDataItem> =
        item.map(::mapTo)

}