package com.sample.app.feature.details.models

sealed interface DetailsAction {
    data object None : DetailsAction
    class OpenUrl(val url: String) : DetailsAction
    class ShowError(val message: String) : DetailsAction
}