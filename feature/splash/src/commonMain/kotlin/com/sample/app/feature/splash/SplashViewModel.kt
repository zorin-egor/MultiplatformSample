package com.sample.app.feature.splash

import androidx.lifecycle.viewModelScope
import com.sample.app.core.ui.viewmodels.UiState
import com.sample.app.core.ui.viewmodels.UiStateViewModel
import com.sample.app.feature.splash.models.SplashActions
import com.sample.app.feature.splash.models.SplashEvents
import com.sample.app.feature.splash.models.SplashUiModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn


class SplashViewModel : UiStateViewModel<SplashUiModel, SplashActions, SplashEvents>(
    initialAction = SplashActions.None
) {

    private var progress = 0

    override val state: StateFlow<UiState<SplashUiModel>> = flow<UiState<SplashUiModel>> {
        cycloidProgress()
        setAction(SplashActions.NavigateToUsers)
    }.catch { error ->
        println(error)
        setAction(SplashActions.ShowError(error))
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = UiState.Success(
            SplashUiModel(
                progress = 0,
                toProgress = 100,
        ))
    )

    override fun setEvent(item: SplashEvents) {
        println("setEvents($item)")

        when(item) {
            SplashEvents.None -> {}
            SplashEvents.OnUserClick -> {
                /* For analytics */
            }
        }
    }

    private suspend fun FlowCollector<UiState<SplashUiModel>>.cycloidProgress() {
        while(true) {
            val state = getLastSuccessStateOrNull<SplashUiModel>() ?: break
            if (progress > state.toProgress) {
                break
            }

            emit(UiState.Success(state.copy(
                progress = progress,
            )))

            progress += 2
            delay(50)
        }
    }

}
