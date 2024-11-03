package com.sample.app.feature.repository_details.models


sealed interface RepositoryDetailsActions {
    data object None : RepositoryDetailsActions
    data class ShowError(val error: Throwable) : RepositoryDetailsActions
    data class ShareUrl(val url: String) : RepositoryDetailsActions
}