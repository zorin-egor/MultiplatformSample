package com.sample.multiplatform.widgets

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun LazyListState.setEdgeEvents(prefetch: Int = 3, onTopList: ((Int) -> Unit)? = null, onBottomList: (Int) -> Unit): Boolean {
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
fun LazyListState.setEdgeEvents(
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