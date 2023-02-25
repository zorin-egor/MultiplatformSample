package com.sample.multiplatform

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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

        Column(modifier = Modifier.fillMaxWidth().fillMaxHeight()) {
            LazyColumn(
                modifier = Modifier.fillMaxWidth().fillMaxHeight(),
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(
                    items = viewState.users,
                    key = { it.id }
                ) {user ->
                    UsersItemContent(user, Modifier.fillMaxWidth().height(250.dp), viewModel::obtainEvent)
                }
            }

            LinearProgressIndicator()
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