package com.sample.app.feature.users

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage


@Composable
fun UsersScreen(
    viewModel: UsersViewModel = viewModel { UsersViewModel() },
    onUserClick: () -> Unit
) {
    println("UsersScreen()")

    val click = viewModel.click.collectAsState()
    val users = viewModel.users.collectAsState()

    if (click.value) {
        onUserClick()
        viewModel.reset()
    }

    Column (modifier = Modifier.fillMaxSize()) {
        Button(onClick = {
            viewModel.onClick()
        },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Click me")
        }

        users.value.forEach {
            AsyncImage(
                model = it.avatarUrl,
                contentDescription = null,
                modifier = Modifier.height(300.dp)
                    .fillMaxWidth()
            )
            Text(
                text = it.login,
                modifier = Modifier.wrapContentHeight()
                    .fillMaxWidth()
            )
        }

    }


}

