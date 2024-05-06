package com.sample.multiplatform


import com.adeo.kviewmodel.BaseSharedViewModel
import com.sample.multiplatform.di.Inject
import com.sample.multiplatform.models.UserModel
import com.sample.multiplatform.models.UsersAction
import com.sample.multiplatform.models.UsersEvent
import com.sample.multiplatform.models.UsersViewState
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class UsersViewModel : BaseSharedViewModel<UsersViewState, UsersAction, UsersEvent>(
    initialState = UsersViewState(isCenterProgress = true, isBottomProgress = false)
) {

    companion object {}

    private val usersRepository: UsersRepository = Inject.instance()
    private var usersJob: Job? = null
    private val users: ArrayList<UserModel> = arrayListOf()

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

                val response = usersRepository.getUsers(since = since, useOnlyCache = true)
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
                viewAction = UsersAction.OpenDetails(viewEvent.user)
            }
            is UsersEvent.ResetAction -> {
                viewAction = null
            }
        }
    }

}