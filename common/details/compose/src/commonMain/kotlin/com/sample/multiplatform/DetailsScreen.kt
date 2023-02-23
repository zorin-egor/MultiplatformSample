package com.sample.multiplatform

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.adeo.kviewmodel.compose.ViewModel
import com.adeo.kviewmodel.compose.observeAsState
import com.sample.multiplatform.models.DetailsAction
import com.sample.multiplatform.models.User
import ru.alexgladkov.odyssey.compose.local.LocalRootController

@Composable
fun DetailsScreen(user: User) {

    val rootController = LocalRootController.current

    ViewModel(factory = { DetailsViewModel().apply {
        setUser(user)
    } }) { viewModel ->
        val viewState = viewModel.viewStates().observeAsState().value
        val viewAction = viewModel.viewActions().observeAsState().value

        Text("Details: ${viewState.details?.id} - ${viewState.details?.login}")

        when (viewAction) {
            is DetailsAction.ShowError -> {}
            is DetailsAction.OpenUrl -> {

            }
            null -> {}
        }
    }
}