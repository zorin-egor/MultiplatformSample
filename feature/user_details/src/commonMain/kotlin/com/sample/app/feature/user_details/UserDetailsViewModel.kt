package com.sample.app.feature.user_details

import androidx.lifecycle.viewModelScope
import com.sample.app.common.di.Inject
import com.sample.app.common.result.Result
import com.sample.app.core.domain.GetUserDetailsUseCase
import com.sample.app.core.model.UserDetailsModel
import com.sample.app.core.ui.viewmodels.UiState
import com.sample.app.core.ui.viewmodels.UiStateViewModel
import com.sample.app.feature.user_details.models.UserDetailsActions
import com.sample.app.feature.user_details.models.UserDetailsEvent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.stateIn


class UserDetailsViewModel(
    getUserDetailsUseCase: GetUserDetailsUseCase = Inject.instance(),
) : UiStateViewModel<UserDetailsModel, UserDetailsActions, UserDetailsEvent>(
    initialAction = UserDetailsActions.None
) {

    private var userDetails: UserDetailsModel? = null
    private val userData = MutableStateFlow<UserDetailsEvent.GetUser?>(null)

    override fun setEvent(item: UserDetailsEvent) {
        when (item) {
            UserDetailsEvent.None -> setAction(UserDetailsActions.None)
            UserDetailsEvent.NavigationBack -> { /* For analytic */ }

            UserDetailsEvent.ShareProfile -> userDetails?.let {
                setAction(UserDetailsActions.ShareUrl(it.url ?: "None"))
            }

            is UserDetailsEvent.GetUser -> userData.tryEmit(item)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override val state: StateFlow<UiState<UserDetailsModel>> = userData.filterNotNull()
        .flatMapLatest {
            getUserDetailsUseCase(userId = it.id, url = it.url)
        }
        .mapNotNull { item ->
            when (item) {
                Result.Loading -> UiState.Loading

                is Result.Error -> {
                    getLastSuccessStateOrNull<UserDetailsModel>()?.let {
                        setAction(UserDetailsActions.ShowError(item.exception))
                        return@mapNotNull null
                    } ?: UiState.Empty
                }

                is Result.Success -> {
                    userDetails = item.data
                    UiState.Success(item.data)
                }
            }
        }.catch { error ->
            println(error)
            setAction(UserDetailsActions.ShowError(error))
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = UiState.Loading,
        )
}