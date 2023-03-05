package com.sample.multiplatform

import androidx.compose.runtime.Composable
import com.adeo.kviewmodel.compose.observeAsState
import com.adeo.kviewmodel.odyssey.StoredViewModel
import com.sample.multiplatform.models.UsersAction
import com.sample.multiplatform.models.UsersNavigation
import com.sample.multiplatform.navigation.NavigationTree
import ru.alexgladkov.odyssey.compose.extensions.push
import ru.alexgladkov.odyssey.compose.local.LocalRootController


@Composable
fun UsersScreen() {

    val rootController = LocalRootController.current

    StoredViewModel(factory = { UsersViewModel() }) { viewModel ->
        val viewState = viewModel.viewStates().observeAsState().value
        val viewAction = viewModel.viewActions().observeAsState().value
        val navigation = viewModel.consumableViewActions().observeAsState().value

        if (viewState.isCenterProgress) {
            EmptyUsersContent()
        } else {
            ItemsUsersContent(viewState, viewModel::obtainEvent)
        }

        when(navigation) {
            is UsersNavigation.OpenDetails -> {
                rootController.push(NavigationTree.Details.DetailsScreen.name, navigation.user)
            }
            null -> {}
        }

        when (viewAction) {
            is UsersAction.ShowError -> {}
            null -> {}
        }
    }
}

