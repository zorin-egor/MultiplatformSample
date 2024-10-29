package com.sample.app.feature.user_details.navigation

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.sample.app.feature.user_details.UserDetailsScreen
import io.ktor.util.decodeBase64String
import io.ktor.util.encodeBase64

const val USER_ID_ARG = "userId"
const val USER_URL_ARG = "userUrl"
const val USER_DETAILS_ROUTE = "user_details_route"
const val USER_DETAILS_ROUTE_PATH = "$USER_DETAILS_ROUTE?$USER_ID_ARG={$USER_ID_ARG}&$USER_URL_ARG={$USER_URL_ARG}"

class UserDetailsArgs(val userId: Long, val userUrl: String) {
    constructor(savedStateHandle: SavedStateHandle) :
            this(
                checkNotNull(savedStateHandle[USER_ID_ARG]),
                checkNotNull(savedStateHandle[USER_URL_ARG]).toString().decodeBase64String()
            )
}

fun NavController.navigateToUserDetails(userId: Long, userUrl: String, navOptions: NavOptionsBuilder.() -> Unit = {}) {
    val encodedUrl = userUrl.encodeBase64()
    val newRoute = "$USER_DETAILS_ROUTE?$USER_ID_ARG=$userId&$USER_URL_ARG=$encodedUrl"
    navigate(newRoute) {
        navOptions()
    }
}

fun NavGraphBuilder.userDetailsScreen(
    onShowSnackbar: suspend (String, String?) -> Boolean
) {
    composable(
        route = USER_DETAILS_ROUTE_PATH,
        arguments = listOf(
            navArgument(USER_ID_ARG) { type = NavType.LongType },
            navArgument(USER_URL_ARG) { type = NavType.StringType },
        ),
    ) {
        UserDetailsScreen(
            UserDetailsArgs(
                it.arguments?.getLong(USER_ID_ARG)!!,
                it.arguments?.getString(USER_URL_ARG)!!.decodeBase64String()
            ),
            onShowSnackbar = onShowSnackbar
        )
    }
}