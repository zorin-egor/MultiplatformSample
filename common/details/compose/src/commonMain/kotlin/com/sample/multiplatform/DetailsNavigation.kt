package com.sample.multiplatform

import com.sample.multiplatform.models.UserModel
import com.sample.multiplatform.navigation.NavigationTree
import ru.alexgladkov.odyssey.compose.extensions.screen
import ru.alexgladkov.odyssey.compose.navigation.RootComposeBuilder


fun RootComposeBuilder.detailsFlow() {
    screen(name = NavigationTree.Details.DetailsScreen.name) {
        DetailsScreen(it as UserModel)
    }
}