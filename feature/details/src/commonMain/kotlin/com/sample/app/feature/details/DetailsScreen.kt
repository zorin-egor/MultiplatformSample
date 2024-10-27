package com.sample.app.feature.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun DetailsScreen(
    viewModel: DetailsViewModel = viewModel { DetailsViewModel() },
) {
    val users = viewModel.users.collectAsState()

    Column (modifier = Modifier.fillMaxSize()) {
        Text(text = users.value.joinToString(separator = "\n") { "${it.login} - ${it.id}" })
    }
}