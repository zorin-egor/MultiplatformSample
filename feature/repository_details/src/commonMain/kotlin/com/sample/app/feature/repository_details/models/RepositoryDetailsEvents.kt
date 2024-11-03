package com.sample.app.feature.repository_details.models


sealed interface RepositoryDetailsEvents {
    data object None : RepositoryDetailsEvents
    data object ShareProfile : RepositoryDetailsEvents
    data object NavigationBack : RepositoryDetailsEvents
    data class GetRepo(val repo: String, val owner: String) : RepositoryDetailsEvents
}