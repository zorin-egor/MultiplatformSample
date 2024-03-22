package com.sample.multiplatform

import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.adeo.kviewmodel.compose.observeAsState
import com.adeo.kviewmodel.odyssey.StoredViewModel
import com.sample.multiplatform.models.SplashAction
import com.sample.multiplatform.models.SplashNavigation
import com.sample.multiplatform.models.cycloid.CycloidColors
import com.sample.multiplatform.models.cycloid.CycloidModel
import com.sample.multiplatform.navigation.NavigationTree
import kotlinx.coroutines.launch
import ru.alexgladkov.odyssey.compose.extensions.push
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import ru.alexgladkov.odyssey.core.LaunchFlag

@Composable
fun SplashScreen() {
    val rootController = LocalRootController.current

    println("SplashScreen()")

    StoredViewModel(factory = { SplashViewModel() }) { viewModel ->
        val viewState = viewModel.viewStates().observeAsState()
        val viewAction = viewModel.viewActions().observeAsState()
        val navigation = viewModel.navigationEvents().observeAsState()
        val scaffoldState = rememberScaffoldState()
        val scope = rememberCoroutineScope()

        println("SplashScreen() - viewModel")

        Scaffold(
            scaffoldState = scaffoldState
        ) {
            val colors = CycloidColors(
                lineDefaultColor = Color( 128, 128, 128, 200),
                lineProgressColor = Color(124, 252, 0, 200),
                particleDefaultColor = Color( 105, 105, 105, 200),
                particleProgressColor = Color( 50, 205, 50, 200),
            )

            ProgressWidget(
                progress = derivedStateOf { viewState.value.progress },
                widgetConfig = ProgressWidgetConfig(
                    shapeType = CycloidModel.Two(
                        colors = colors,
                        lineWidth = 7.dp.value,
                        particleRadius = 20.dp.value
                    ),
                    fromProgress = 0,
                    toProgress = viewModel.viewStates().replayCache.firstOrNull()?.toProgress
                        ?: viewState.value.toProgress
                )
            )
        }

        when(val result = viewAction.value) {
            is SplashAction.ShowError -> {
                scope.launch {
                    scaffoldState.snackbarHostState.showSnackbar(result.message)
                }
            }
            else -> {}
        }

        when(val result = navigation.value) {
            is SplashNavigation.OpenUsers -> {
                println("SplashScreen() - navigation")
                rootController.push(
                    screen = NavigationTree.Users.UsersScreen.name,
                    launchFlag = LaunchFlag.ClearPrevious,
                )
            }
            else -> {}
        }
    }
}