package com.sample.app.feature.repository_details.models


sealed interface RepositoriesActions {
    data object None : RepositoriesActions
    data class ShowError(val error: Throwable) : RepositoriesActions
    data class NavigationToDetails(val owner: String, val name: String) : RepositoriesActions
}