package com.sample.multiplatform

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.adeo.kviewmodel.compose.observeAsState
import com.adeo.kviewmodel.odyssey.StoredViewModel
import com.sample.multiplatform.models.UsersAction
import com.sample.multiplatform.models.UsersEvent
import com.sample.multiplatform.models.UsersNavigation
import com.sample.multiplatform.models.UsersViewState
import com.sample.multiplatform.navigation.NavigationTree
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
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

@Composable
fun EmptyUsersContent() {
    Box(modifier = Modifier.fillMaxSize()) {
        CircularProgressIndicator(modifier = Modifier.size(50.dp).align(Alignment.Center))
    }
}

@Composable
fun ItemsUsersContent(viewState: UsersViewState, obtainEvent: (UsersEvent) -> Unit) {
    val listState = rememberLazyListState()

    listState.setEdgeEvents(
        debounce = 1000,
        prefetch = 3,
        onTopList = { index ->
            println("ItemsUsersContent - onTopList: $index")
        },
        onBottomList = { index ->
            println("ItemsUsersContent - onBottomList: $index")
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
                key = { it.id }
            ) { user ->
                UsersItemContent(user, Modifier.fillMaxWidth().height(250.dp), obtainEvent)
            }
        }

        if (viewState.isBottomProgress) {
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth().height(4.dp))
        }
    }
}

@Composable
private fun LazyListState.setEdgeEvents(prefetch: Int = 3, onTopList: ((Int) -> Unit)? = null, onBottomList: (Int) -> Unit): Boolean {
    return remember(this) {
        derivedStateOf {
            val totalItems = layoutInfo.totalItemsCount
            val lastItem = layoutInfo.visibleItemsInfo.lastOrNull()
            val firstItem = layoutInfo.visibleItemsInfo.firstOrNull()
            val lastIndex = lastItem?.index ?: 0
            val firstIndex = firstItem?.index ?: 0

            when {
                layoutInfo.totalItemsCount == 0 -> false
                lastIndex + prefetch > totalItems -> {
                    onBottomList(lastIndex)
                    true
                }
                firstIndex - prefetch < 0 -> {
                    onTopList?.run {
                        invoke(firstIndex)
                        true
                    } ?: false
                }
                else -> false
            }
        }
    }.value
}

@Composable
private fun LazyListState.setEdgeEvents(
    debounce: Long = 500L,
    prefetch: Int = 3,
    onTopList: ((Int) -> Unit)? = null,
    onBottomList: (Int) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    var eventJob: Job? = remember { null }

    setEdgeEvents(
        prefetch = prefetch,
        onTopList = onTopList@ { index ->
            if (eventJob?.isActive == true) return@onTopList
            eventJob = coroutineScope.launch {
                onTopList?.invoke(index)
                delay(debounce)
            }
        },
        onBottomList = onBottomList@ { index ->
            if (eventJob?.isActive == true) return@onBottomList
            eventJob = coroutineScope.launch {
                onBottomList(index)
                delay(debounce)
            }
        }
    )
}
