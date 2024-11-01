package com.sample.app.feature.repositories.models

import com.sample.app.core.model.RepositoryModel


sealed interface RepositoriesEvents {
    data object None : RepositoriesEvents
    data class OnRepositoryClick(val item: RepositoryModel): RepositoriesEvents
    data object NextRepositories : RepositoriesEvents
    data class SearchQuery(val query: String) : RepositoriesEvents
}