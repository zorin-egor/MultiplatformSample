package com.sample.multiplatform

import com.adeo.kviewmodel.BaseSharedViewModel
import com.sample.multiplatform.di.Inject
import com.sample.multiplatform.models.User
import com.sample.multiplatform.models.UsersAction
import com.sample.multiplatform.models.UsersEvent
import com.sample.multiplatform.models.UsersViewState
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class UsersViewModel : BaseSharedViewModel<UsersViewState, UsersAction, UsersEvent>(
    initialState = UsersViewState(isCenterProgress = true, isBottomProgress = false)
) {

    private val usersRepository: UsersRepository = Inject.instance()
    private var usersJob: Job? = null
    private val users: ArrayList<User> = arrayListOf()

    init {
        getUsers(0)
    }

    private fun getUsers(since: Long) {
        usersJob = viewModelScope.launch {
            try {
                viewState = viewState.copy(
                    isCenterProgress = since == 0L,
                    isBottomProgress = since != 0L
                )

                val response = usersRepository.getUsers(since)
                if (response.isNotEmpty()) {
                    if (since == 0L) {
                        users.clear()
                    }
                    users.addAll(response)
                }

                viewState = viewState.copy(
                    users = users.toList(),
                    isCenterProgress = false,
                    isBottomProgress = false
                )
            } catch (e: Exception) {
                viewAction = UsersAction.ShowError(e.message ?: "Unknown error")
                viewState = viewState.copy(isCenterProgress = false, isBottomProgress = false)
            }
        }
    }

    override fun obtainEvent(viewEvent: UsersEvent) {
        when (viewEvent) {
            is UsersEvent.OnBottomEnd -> {}
            is UsersEvent.OnUserClick -> {
                viewAction = UsersAction.OpenDetails(viewEvent.user)
            }
        }
    }

}