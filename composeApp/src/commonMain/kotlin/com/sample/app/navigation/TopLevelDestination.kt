package com.sample.app.navigation

import androidx.compose.ui.graphics.vector.ImageVector
import com.sample.app.core.ui.icon.AppIcons
import com.sample.app.feature.repositories.resources.feature_repositories_title_short
import com.sample.app.feature.repository_details.navigation.REPOSITORIES_ROUTE
import com.sample.app.feature.users.navigation.USERS_ROUTE
import com.sample.app.feature.users.resources.feature_users_title
import org.jetbrains.compose.resources.StringResource
import com.sample.app.feature.repositories.resources.Res as FeatureRepoRes
import com.sample.app.feature.users.resources.Res as FeatureUserRes

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
        iconText = FeatureUserRes.string.feature_users_title
    ),
    REPOSITORIES(
        route = REPOSITORIES_ROUTE,
        selectedIcon = AppIcons.Repositories,
        unselectedIcon = AppIcons.RepositoriesBorder,
        iconText = FeatureRepoRes.string.feature_repositories_title_short,
    ),
}
