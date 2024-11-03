package com.sample.app.feature.repository_details.models

import org.jetbrains.compose.resources.StringResource


sealed interface RepositoryDetailsActions {
    data object None : RepositoryDetailsActions
    data class ShowError(val error: StringResource) : RepositoryDetailsActions
    data class ShareUrl(val url: String) : RepositoryDetailsActions
}