package com.sample.app

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.sample.app.feature.details.DetailsScreen
import com.sample.app.feature.users.UsersScreen
import multiplatformsample.composeapp.generated.resources.Res
import multiplatformsample.composeapp.generated.resources.feature_details_title
import multiplatformsample.composeapp.generated.resources.feature_users_title
import org.jetbrains.compose.resources.StringResource

enum class GitHubScreen(val title: StringResource) {
    Users(title = Res.string.feature_users_title),
    Details(title = Res.string.feature_details_title),
}

@Composable
fun GitHubApp(
    navController: NavHostController = rememberNavController()
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = GitHubScreen.valueOf(
        backStackEntry?.destination?.route ?: GitHubScreen.Users.name
    )

    Scaffold { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = GitHubScreen.Users.name,
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
        ) {
            composable(route = GitHubScreen.Users.name) {
                UsersScreen {
                    navController.navigate(GitHubScreen.Details.name)
                }
            }
            composable(route = GitHubScreen.Details.name) {
                DetailsScreen()
            }
        }
    }

}