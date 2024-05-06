package com.sample.multiplatform

import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import com.adeo.kviewmodel.compose.observeAsState
import com.adeo.kviewmodel.odyssey.StoredViewModel
import com.sample.multiplatform.models.UsersAction
import com.sample.multiplatform.models.UsersEvent
import com.sample.multiplatform.navigation.NavigationTree
import kotlinx.coroutines.launch
import ru.alexgladkov.odyssey.compose.extensions.push
import ru.alexgladkov.odyssey.compose.local.LocalRootController


@Composable
fun UsersScreen(lastSince: Long) {
    println("UsersScreen()")

    val rootController = LocalRootController.current

    StoredViewModel(factory = { UsersViewModel() }) { viewModel ->
        println("UsersScreen() - viewModel")

        val viewState = viewModel.viewStates().observeAsState()
        val viewAction = viewModel.viewActions().observeAsState()
        val scaffoldState = rememberScaffoldState()
        val scope = rememberCoroutineScope()

        if (viewState.value.isCenterProgress) {
            EmptyUsersContent()
        } else {
            Scaffold(
                scaffoldState = scaffoldState
            ) {
                ItemsUsersContent(viewState.value, viewModel::obtainEvent)
            }
        }

        when (val result = viewAction.value) {
            is UsersAction.ShowError -> {
                scope.launch {
                    scaffoldState.snackbarHostState.showSnackbar(result.message)
                }
            }
            is UsersAction.OpenDetails -> {
                rootController.push(NavigationTree.Details.DetailsScreen.name, result.user)
                viewModel.obtainEvent(UsersEvent.ResetAction)
            }

            else -> {}
        }
    }
}

