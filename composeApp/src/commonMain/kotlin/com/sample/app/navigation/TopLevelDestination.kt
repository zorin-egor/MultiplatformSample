package com.sample.app.navigation

import com.sample.app.feature.users.navigation.USERS_ROUTE

enum class TopLevelDestination(
    val route: String
) {
    USERS(
        route = USERS_ROUTE
    )
}
