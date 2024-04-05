package com.sample.multiplatform

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import com.adeo.kviewmodel.compose.ViewModel
import com.adeo.kviewmodel.compose.observeAsState
import com.sample.multiplatform.image_loader.loadImage
import com.sample.multiplatform.models.DetailsAction
import com.sample.multiplatform.models.UserModel

@Composable
fun DetailsScreen(user: UserModel) {
    ViewModel(factory = { DetailsViewModel().apply {
        getDetails(user.id, user.url)
    } }) { viewModel ->
        val viewState = viewModel.viewStates().observeAsState().value
        val viewAction = viewModel.viewActions().observeAsState().value
        val painter = loadImage(user.avatarUrl)

        Column(modifier = Modifier.fillMaxWidth().fillMaxHeight()) {
            Image(
                painter = painter,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxWidth().align(Alignment.CenterHorizontally).weight(weight = 1.0f)
            )
            Text("Details: ${viewState.details?.id} - ${viewState.details?.login}")
        }

        when (viewAction) {
            is DetailsAction.ShowError -> {}
            is DetailsAction.OpenUrl -> {}
            null -> {}
        }
    }
}