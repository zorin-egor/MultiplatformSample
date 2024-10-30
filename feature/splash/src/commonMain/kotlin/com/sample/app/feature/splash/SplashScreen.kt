package com.sample.app.feature.splash

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sample.app.core.ui.viewmodels.UiState
import com.sample.app.feature.splash.models.SplashActions
import com.sample.app.feature.splash.models.SplashEvents
import com.sample.app.widget.cycloid.ProgressWidget
import com.sample.app.widget.cycloid.ProgressWidgetConfig
import com.sample.app.widget.cycloid.models.cycloid.CycloidModel


@Composable
fun SplashScreen(
    onNavigationEvent: () -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    viewModel: SplashViewModel = viewModel { SplashViewModel() }
) {
    println("SplashScreen()")

    val usersUiState by viewModel.state.collectAsStateWithLifecycle()
    val usersAction by viewModel.action.collectAsStateWithLifecycle()

    println("SplashScreen() - state: ${usersUiState.hashCode()}, $usersAction")

    when(val state = usersUiState) {
        is UiState.Success -> {
            val widgetConfig = remember {
                ProgressWidgetConfig(
                    shapeType = CycloidModel.Two(
                        colors = state.item.cycloidColors,
                        lineWidth = 2.dp.value,
                        particleRadius = 10.dp.value
                    ),
                    fromProgress = 0,
                    toProgress = state.item.toProgress
                )
            }

            ProgressWidget(
                progress = state.item.progress,
                widgetConfig = widgetConfig,
                modifier = Modifier
                    .size(300.dp)
                    .clickable {
                        viewModel.setEvent(SplashEvents.OnUserClick)
                    }
            )
        }

        else -> {
            throw IllegalStateException("Wrong states for screen")
        }
    }

    when(val action = usersAction) {
        SplashActions.None -> {}

        is SplashActions.NavigateToUsers -> {
            onNavigationEvent()
            viewModel.setEvent(SplashEvents.None)
        }

        is SplashActions.ShowError -> {
            println("User action: $action")
            LaunchedEffect(key1 = action) {
                onShowSnackbar(action.error.message ?: "Oops", null)
                viewModel.setEvent(SplashEvents.None)
            }
        }

    }
}

