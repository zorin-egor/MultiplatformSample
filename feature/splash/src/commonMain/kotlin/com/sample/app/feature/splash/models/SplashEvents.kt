package com.sample.app.feature.splash.models

sealed interface SplashEvents {
    data object None : SplashEvents
    data object OnUserClick: SplashEvents
}