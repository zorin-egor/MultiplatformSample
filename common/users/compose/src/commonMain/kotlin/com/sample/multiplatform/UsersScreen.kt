package com.sample.multiplatform

import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import com.adeo.kviewmodel.compose.observeAsState
import com.adeo.kviewmodel.odyssey.StoredViewModel
import com.sample.multiplatform.models.UsersAction
import com.sample.multiplatform.models.UsersNavigation
import com.sample.multiplatform.navigation.NavigationTree
import kotlinx.coroutines.launch
import ru.alexgladkov.odyssey.compose.extensions.push
import ru.alexgladkov.odyssey.compose.local.LocalRootController


@Composable
fun UsersScreen() {
    println("UsersScreen()")

    val rootController = LocalRootController.current

    StoredViewModel(factory = { UsersViewModel() }) { viewModel ->
        println("UsersScreen() - viewModel")

        val viewState = viewModel.viewStates().observeAsState().value
        val viewAction = viewModel.viewActions().observeAsState()
        val navigation = viewModel.navigationEvents().observeAsState()
        val scaffoldState = rememberScaffoldState()
        val scope = rememberCoroutineScope()

        if (viewState.isCenterProgress) {
            EmptyUsersContent()
        } else {
            Scaffold(
                scaffoldState = scaffoldState
            ) {
                ItemsUsersContent(viewState, viewModel::obtainEvent)
            }
        }

        when(val result = navigation.value) {
            is UsersNavigation.OpenDetails -> {
                rootController.push(NavigationTree.Details.DetailsScreen.name, result.user)
            }
            null -> {}
        }

        when (val result = viewAction.value) {
            is UsersAction.ShowError -> {
                scope.launch {
                    scaffoldState.snackbarHostState.showSnackbar(result.message)
                }
            }
            null -> {}
        }
    }
}

