package com.sample.app.feature.repository_details

import androidx.lifecycle.viewModelScope
import com.sample.app.core.common.di.Inject
import com.sample.app.core.common.result.Result
import com.sample.app.core.domain.GetRepositoryDetailsByOwnerUseCase
import com.sample.app.core.model.RepositoryDetailsModel
import com.sample.app.core.ui.ext.toStringResource
import com.sample.app.core.ui.viewmodels.UiState
import com.sample.app.core.ui.viewmodels.UiStateViewModel
import com.sample.app.feature.repository_details.models.RepositoryDetailsActions
import com.sample.app.feature.repository_details.models.RepositoryDetailsEvents
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.stateIn


class RepositoryDetailsViewModel(
    getRepositoryDetailsByIdUseCase: GetRepositoryDetailsByOwnerUseCase = Inject.instance(),
) : UiStateViewModel<RepositoryDetailsModel, RepositoryDetailsActions, RepositoryDetailsEvents>(
    initialAction = RepositoryDetailsActions.None
) {

    private val repoData = MutableStateFlow<RepositoryDetailsEvents.GetRepo?>(null)

    private var repositoryDetails: RepositoryDetailsModel? = null

    override val state: StateFlow<UiState<RepositoryDetailsModel>> = repoData.filterNotNull()
        .flatMapLatest { getRepositoryDetailsByIdUseCase(owner = it.owner, repo = it.repo) }
        .mapNotNull { item ->
            when(item) {
                Result.Loading -> UiState.Loading

                is Result.Error -> {
                    getLastSuccessStateOrNull<RepositoryDetailsModel>()?.let {
                        setAction(RepositoryDetailsActions.ShowError(item.exception.toStringResource))
                        return@mapNotNull null
                    } ?: UiState.Empty
                }

                is Result.Success -> {
                    repositoryDetails = item.data
                    UiState.Success(item.data)
                }
            }
        }.catch { error ->
            println(error)
            setAction(RepositoryDetailsActions.ShowError(error.toStringResource))
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = UiState.Loading,
        )

    override fun setEvent(item: RepositoryDetailsEvents) {
        when(item) {
            RepositoryDetailsEvents.None -> setAction(RepositoryDetailsActions.None)
            RepositoryDetailsEvents.NavigationBack -> { /* For analytic */ }
            RepositoryDetailsEvents.ShareProfile -> repositoryDetails?.let { setAction(
                RepositoryDetailsActions.ShareUrl(it.htmlUrl)) }
            is RepositoryDetailsEvents.GetRepo -> repoData.tryEmit(item)
        }
    }
}