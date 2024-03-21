package com.sample.multiplatform.models

sealed interface SplashNavigation {
    class OpenUsers(val users: List<User>?) : SplashNavigation

}