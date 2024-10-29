package com.sample.app.navigation

import multiplatformsample.composeapp.generated.resources.Res
import multiplatformsample.composeapp.generated.resources.app_feature_details_title
import multiplatformsample.composeapp.generated.resources.app_feature_users_title
import org.jetbrains.compose.resources.StringResource

enum class NavHostScreens(val title: StringResource) {
    Users(title = Res.string.app_feature_users_title),
    Details(title = Res.string.app_feature_details_title),
}