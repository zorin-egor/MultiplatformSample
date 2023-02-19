package com.sample.multiplatform


import com.sample.multiplatform.navigation.NavigationTree
import ru.alexgladkov.odyssey.compose.extensions.screen
import ru.alexgladkov.odyssey.compose.navigation.RootComposeBuilder

fun RootComposeBuilder.usersFlow() {
    screen(name = NavigationTree.Users.UsersScreen.name) {
        UsersScreen()
    }
}