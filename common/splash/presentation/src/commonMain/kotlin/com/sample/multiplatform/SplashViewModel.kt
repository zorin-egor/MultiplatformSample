package com.sample.multiplatform


import SplashEvent
import com.adeo.kviewmodel.BaseSharedViewModel
import com.sample.multiplatform.di.Inject
import com.sample.multiplatform.models.SplashAction
import com.sample.multiplatform.models.SplashViewState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SplashViewModel : BaseSharedViewModel<SplashViewState, SplashAction, SplashEvent>(
    initialState = SplashViewState(
        progress = 0,
        toProgress = 110
    )
) {

    companion object {}

    private val usersRepository: UsersRepository = Inject.instance()

    init {
        getUsers()
    }

    private fun getUsers() {
        viewModelScope.launch {
            val progressJob = launch {
                var progress = 0
                while(progress <= viewState.toProgress) {
                    viewState = viewState.copy(progress = progress)
                    progress += 2
                    delay(30)
                }
            }.apply {
                invokeOnCompletion {
                    if (it != null) {
                        viewState = viewState.copy(progress = viewState.toProgress)
                    }
                }
            }

            val response = runCatching { usersRepository.getUsers(since = 0) }
            val result = response.getOrNull()

            when {
                response.isFailure -> {
                    viewAction = SplashAction.ShowError(response.exceptionOrNull()?.toString() ?: "Unknown Error")
                    return@launch
                }
                result.isNullOrEmpty() -> {
                    viewAction = SplashAction.ShowError("No data")
                    return@launch
                }
            }

            if (progressJob.isActive) {
                delay(1000)
            }

            progressJob.takeIf { it.isActive }?.cancel()

            viewAction = SplashAction.OpenUsers(lastSince = result!!.last().id)
        }
    }

    override fun obtainEvent(viewEvent: SplashEvent) {
        when (viewEvent) {
            else -> {}
        }
    }

}