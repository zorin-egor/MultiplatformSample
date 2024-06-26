package com.sample.multiplatform.models

sealed interface SplashAction {
    class ShowError(val message: String) : SplashAction

    class OpenUsers(val lastSince: Long) : SplashAction
}