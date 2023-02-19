package com.sample.multiplatform

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.adeo.kviewmodel.compose.ViewModel
import com.adeo.kviewmodel.compose.observeAsState
import com.sample.multiplatform.models.UsersAction
import com.sample.multiplatform.models.UsersEvent
import com.sample.multiplatform.navigation.NavigationTree
import ru.alexgladkov.odyssey.compose.extensions.push
import ru.alexgladkov.odyssey.compose.local.LocalRootController

@Composable
fun UsersScreen() {

    val rootController = LocalRootController.current

    ViewModel(factory = { UsersViewModel() }) { viewModel ->
        val viewState = viewModel.viewStates().observeAsState().value
        val viewAction = viewModel.viewActions().observeAsState().value

        Column(modifier = Modifier.fillMaxWidth().fillMaxHeight()) {
            LazyColumn(modifier = Modifier.fillMaxWidth().fillMaxHeight()) {
                items(
                    items = viewState.users,
                    key = { it.id }
                ) {
                    Text(
                        text = it.login.toString(),
                        modifier = Modifier.clickable {
                            viewModel.obtainEvent(UsersEvent.OnUserClick(it))
                        }
                    )
                }
            }

            LinearProgressIndicator()
        }

        when (viewAction) {
            is UsersAction.ShowError -> {}
            is UsersAction.OpenDetails -> {
                rootController.push(NavigationTree.Details.DetailsScreen.name, viewAction.user)
            }
            null -> {}
        }
    }
}