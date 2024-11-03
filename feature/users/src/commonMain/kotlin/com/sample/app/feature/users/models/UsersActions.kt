package com.sample.app.feature.users.models

import org.jetbrains.compose.resources.StringResource


sealed interface UsersActions {
    data object None : UsersActions
    data class ShowError(val error: StringResource) : UsersActions
    data class NavigateToDetails(val id: Long, val url: String) : UsersActions
}