package com.sample.app.navigation

import androidx.compose.ui.graphics.vector.ImageVector
import com.sample.app.core.ui.icon.AppIcons
import com.sample.app.feature.repository_details.navigation.REPOSITORIES_ROUTE
import com.sample.app.feature.users.navigation.USERS_ROUTE
import multiplatformsample.composeapp.generated.resources.Res
import multiplatformsample.composeapp.generated.resources.app_name
import org.jetbrains.compose.resources.StringResource

enum class TopLevelDestination(
    val route: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val iconText: StringResource,
) {
    USERS(
        route = USERS_ROUTE,
        selectedIcon = AppIcons.User,
        unselectedIcon = AppIcons.UserBorder,
        iconText = Res.string.app_name
    ),
    REPOSITORIES(
        route = REPOSITORIES_ROUTE,
        selectedIcon = AppIcons.Repositories,
        unselectedIcon = AppIcons.RepositoriesBorder,
        iconText = Res.string.app_name,
    ),
}
