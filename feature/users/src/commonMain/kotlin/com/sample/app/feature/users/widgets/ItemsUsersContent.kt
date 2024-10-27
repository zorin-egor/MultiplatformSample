package com.sample.app.feature.users.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sample.app.core.ui.widgets.setEdgeEvents
import com.sample.app.feature.users.models.UsersEvent
import com.sample.app.feature.users.models.UsersViewState

@Composable
fun ItemsUsersContent(viewState: UsersViewState, obtainEvent: (UsersEvent) -> Unit) {
    println("ItemsUsersContent()")

    val listState = rememberLazyListState()

    listState.setEdgeEvents(
        debounce = 1000,
        prefetch = 3,
        onTopList = { index ->
            println("ItemsUsersContent() - onTopList: $index")
        },
        onBottomList = { index ->
            println("ItemsUsersContent() - onBottomList: $index")
            obtainEvent(UsersEvent.OnBottomEnd)
        }
    )

    Column(modifier = Modifier.fillMaxWidth().fillMaxHeight()) {
        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxWidth().weight(weight = 1.0f),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(
                items = viewState.users,
                key = { it }
            ) { user ->
                UsersItemContent(user, Modifier.fillMaxWidth().height(250.dp), obtainEvent)
            }
        }

        if (viewState.isBottomProgress) {
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth().height(4.dp))
        }
    }
}