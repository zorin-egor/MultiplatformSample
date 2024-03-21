package com.sample.multiplatform


import com.sample.multiplatform.di.Inject
import com.sample.multiplatform.models.User
import com.sample.multiplatform.models.UsersAction
import com.sample.multiplatform.models.UsersEvent
import com.sample.multiplatform.models.UsersNavigation
import com.sample.multiplatform.models.UsersViewState
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class UsersViewModel : BaseViewModel<UsersViewState, UsersAction, UsersEvent, UsersNavigation>(
    initialState = UsersViewState(isCenterProgress = true, isBottomProgress = false)
) {

    companion object {}

    private val usersRepository: UsersRepository = Inject.instance()
    private var usersJob: Job? = null
    private val users: ArrayList<User> = arrayListOf()

    init {
        getUsers()
    }

    fun getUsers(isColdStart: Boolean = users.isEmpty()) {
        println("UsersViewModel-getUsers()-try")

        if (usersJob?.isActive == true) {
            return
        }

        usersJob = viewModelScope.launch {
            try {
                println("UsersViewModel-getUsers()-inside")

                viewState = viewState.copy(
                    isCenterProgress = isColdStart,
                    isBottomProgress = !isColdStart
                )

                val since = if (isColdStart || users.isEmpty()) {
                    UsersRepository.DEFAULT_SINCE_USER
                } else {
                    users.last().id
                }

                val response = usersRepository.getUsers(since)
                if (response.isNotEmpty()) {
                    if (isColdStart) {
                        users.clear()
                    }
                    users.addAll(response)
                }

                viewState = viewState.copy(
                    users = users.toList(),
                    isCenterProgress = false,
                    isBottomProgress = false
                )

                println("UsersViewModel-getUsers()-end")
            } catch (e: Exception) {
                viewAction = UsersAction.ShowError(e.message ?: "Unknown error")
                viewState = viewState.copy(isCenterProgress = false, isBottomProgress = false)
                println("UsersViewModel-getUsers()-error")
            }
        }
    }

    override fun obtainEvent(viewEvent: UsersEvent) {
        when (viewEvent) {
            is UsersEvent.OnBottomEnd -> {
                getUsers(false)
            }
            is UsersEvent.OnUserClick -> {
                _navigation.trySend(UsersNavigation.OpenDetails(viewEvent.user))
            }
        }
    }

}