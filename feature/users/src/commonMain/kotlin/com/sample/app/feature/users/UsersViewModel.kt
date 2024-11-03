package com.sample.app.feature.users

import androidx.lifecycle.viewModelScope
import com.sample.app.core.common.di.Inject
import com.sample.app.core.common.result.Result
import com.sample.app.core.domain.GetUsersUseCase
import com.sample.app.core.ui.ext.toStringResource
import com.sample.app.core.ui.viewmodels.UiState
import com.sample.app.core.ui.viewmodels.UiStateViewModel
import com.sample.app.feature.users.models.UsersActions
import com.sample.app.feature.users.models.UsersEvents
import com.sample.app.feature.users.models.UsersUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update


class UsersViewModel(
    private val getSearchContentsUseCase: GetUsersUseCase = Inject.instance(),
) : UiStateViewModel<UsersUiModel, UsersActions, UsersEvents>(
    initialAction = UsersActions.None
) {

    private val _nextUsers = MutableStateFlow(true)

    override val state: StateFlow<UiState<UsersUiModel>> = _nextUsers.filter { it }
        .flatMapLatest {
            println("UsersViewModel() - flatMapConcat: $it")
            _nextUsers.update { false }
            getSearchContentsUseCase()
        }.mapNotNull { item ->
            println("UsersViewModel() - mapNotNull: $item")

            when(item) {
                Result.Loading -> getLastSuccessStateOrNull<UsersUiModel>()
                    ?.let { UiState.Success(it.copy(isBottomProgress = true)) }
                    ?: UiState.Loading

                is Result.Error -> {
                    getLastSuccessStateOrNull<UsersUiModel>()?.let {
                        setAction(UsersActions.ShowError(item.exception.toStringResource))
                        return@mapNotNull null
                    } ?: UiState.Empty
                }

                is Result.Success -> {
                    if (item.data.isEmpty()) {
                        UiState.Empty
                    } else {
                        UiState.Success(UsersUiModel(users = item.data, isBottomProgress = false))
                    }
                }
            }
        }.catch { error ->
            println(error)
            setAction(UsersActions.ShowError(error.toStringResource))
            updateSuccessState<UsersUiModel> {
                it.copy(isBottomProgress = false)
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = UiState.Loading
        )

    override fun setEvent(item: UsersEvents) {
        println("setEvents($item)")

        when(item) {
            UsersEvents.NextUser -> _nextUsers.tryEmit(true)

            is UsersEvents.OnUserClick -> setAction(
                UsersActions.NavigateToDetails(
                id = item.item.id,
                url = item.item.url
            ))

            UsersEvents.None -> setAction(UsersActions.None)
        }
    }

}
