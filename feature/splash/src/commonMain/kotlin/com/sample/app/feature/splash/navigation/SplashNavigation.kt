package com.sample.app.feature.splash.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.sample.app.feature.splash.SplashScreen


const val SPLASH_ROUTE = "splash_route"

fun NavController.navigateToSplash(navOptions: NavOptions? = null) {
    val newRoute = SPLASH_ROUTE
    navigate(newRoute, navOptions)
}

fun NavGraphBuilder.splashScreen(
    onNavigationEvent: () -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean
) {
    composable(
        route = SPLASH_ROUTE,
    ) {
        SplashScreen(onNavigationEvent = onNavigationEvent, onShowSnackbar = onShowSnackbar)
    }
}