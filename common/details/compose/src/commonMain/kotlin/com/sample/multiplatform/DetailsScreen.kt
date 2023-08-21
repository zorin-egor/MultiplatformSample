package com.sample.multiplatform

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import com.adeo.kviewmodel.compose.ViewModel
import com.adeo.kviewmodel.compose.observeAsState
import com.sample.multiplatform.image_loader.NullDataInterceptor
import com.sample.multiplatform.models.DetailsAction
import com.sample.multiplatform.models.User
import com.seiko.imageloader.model.ImageRequest
import com.seiko.imageloader.rememberImageAction
import com.seiko.imageloader.rememberImageActionPainter
import ru.alexgladkov.odyssey.compose.local.LocalRootController

@Composable
fun DetailsScreen(user: User) {

    val rootController = LocalRootController.current

    ViewModel(factory = { DetailsViewModel().apply {
        setUser(user)
    } }) { viewModel ->
        val viewState = viewModel.viewStates().observeAsState().value
        val viewAction = viewModel.viewActions().observeAsState().value

        val request = remember(user.avatarUrl, 3) {
            ImageRequest {
                data(user.avatarUrl)
                addInterceptor(NullDataInterceptor)
            }
        }
        val action = rememberImageAction(request).value
        val painter = rememberImageActionPainter(action)

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
            is DetailsAction.OpenUrl -> {

            }
            null -> {}
        }
    }
}