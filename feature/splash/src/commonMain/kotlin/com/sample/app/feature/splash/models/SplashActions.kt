package com.sample.app.feature.splash.models


sealed interface SplashActions {
    data object None : SplashActions
    data class ShowError(val error: Throwable) : SplashActions
    data object NavigateToUsers : SplashActions
}