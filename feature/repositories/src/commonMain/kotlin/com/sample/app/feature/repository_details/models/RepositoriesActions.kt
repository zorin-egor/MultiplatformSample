package com.sample.app.feature.repository_details.models

import org.jetbrains.compose.resources.StringResource


sealed interface RepositoriesActions {
    data object None : RepositoriesActions
    data class ShowError(val error: StringResource) : RepositoriesActions
    data class NavigationToDetails(val owner: String, val name: String) : RepositoriesActions
}