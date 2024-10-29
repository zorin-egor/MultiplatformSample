package com.sample.app

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.sample.app.core.ui.components.AppBackground
import com.sample.app.feature.details.DetailsScreen
import com.sample.app.feature.users.UsersScreen


@Composable
fun MainScreen(
    navController: NavHostController = rememberNavController()
) {
    AppBackground {
        val backStackEntry by navController.currentBackStackEntryAsState()
        val currentScreen = AppScreens.valueOf(
            backStackEntry?.destination?.route ?: AppScreens.Users.name
        )

        val snackbarHostState = remember { SnackbarHostState() }

        Scaffold(
            contentColor = MaterialTheme.colors.onBackground,
            contentWindowInsets = WindowInsets.safeDrawing,
            snackbarHost = { SnackbarHost(snackbarHostState) },
            modifier = Modifier.imePadding()
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = AppScreens.Users.name,
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(innerPadding)
            ) {
                composable(route = AppScreens.Users.name) {
                    UsersScreen {
                        navController.navigate(AppScreens.Details.name)
                    }
                }
                composable(route = AppScreens.Details.name) {
                    DetailsScreen()
                }
            }
        }
    }
}