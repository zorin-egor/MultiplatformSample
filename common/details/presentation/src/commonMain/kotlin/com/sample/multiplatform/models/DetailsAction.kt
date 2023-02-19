package com.sample.multiplatform.models

sealed interface DetailsAction {
    class OpenUrl(val url: String) : DetailsAction
    class ShowError(val message: String) : DetailsAction
}