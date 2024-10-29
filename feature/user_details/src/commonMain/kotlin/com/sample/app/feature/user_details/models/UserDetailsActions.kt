package com.sample.app.feature.user_details.models


sealed interface UserDetailsActions {
    data object None : UserDetailsActions
    data class ShowError(val error: Throwable) : UserDetailsActions
    data class ShareUrl(val url: String) : UserDetailsActions
}