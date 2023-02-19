package com.sample.multiplatform.navigation

import com.sample.multiplatform.detailsFlow
import com.sample.multiplatform.usersFlow
import ru.alexgladkov.odyssey.compose.navigation.RootComposeBuilder

fun RootComposeBuilder.generateGraph(source: NavigationSource) {
    usersFlow()
    detailsFlow()
}
