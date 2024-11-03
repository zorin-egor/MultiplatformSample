package com.sample.app.feature.user_details.models

import org.jetbrains.compose.resources.StringResource


sealed interface UserDetailsActions {
    data object None : UserDetailsActions
    data class ShowError(val error: StringResource) : UserDetailsActions
    data class ShareUrl(val url: String) : UserDetailsActions
}