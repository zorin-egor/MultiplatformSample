package com.sample.app.feature.user_details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sample.app.core.ui.components.AppTopBar
import com.sample.app.core.ui.icon.AppIcons
import com.sample.app.core.ui.viewmodels.UiState
import com.sample.app.core.ui.widgets.CircularContent
import com.sample.app.feature.user_details.models.UserDetailsActions
import com.sample.app.feature.user_details.models.UserDetailsEvent
import com.sample.app.feature.user_details.navigation.UserDetailsArgs
import com.sample.app.feature.user_details.widgets.UserDetailsContent
import multiplatformsample.feature.user_details.generated.resources.Res
import multiplatformsample.feature.user_details.generated.resources.feature_details_title
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserDetailsScreen(
    args: UserDetailsArgs,
    onBackClick: () -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    viewModel: UserDetailsViewModel = viewModel { UserDetailsViewModel() },
) {
    remember { viewModel.setEvent(UserDetailsEvent.GetUser(args.userId, args.userUrl)) }

    val detailsState by viewModel.state.collectAsStateWithLifecycle()
    val detailsAction by viewModel.action.collectAsStateWithLifecycle()

//    val topBarNavigationState = topBarViewModel?.collect(key = USER_DETAILS_ROUTE_PATH)
//        ?.collectAsStateWithLifecycle(initialValue = TopBarNavigationState.None)
//
//    when (val action = topBarNavigationState?.value) {
//        is TopBarNavigationState.Menu -> {
//            println("TopBarNavigationState: menu")
//            viewModel.setEvent(UserDetailsEvent.ShareProfile)
//            topBarViewModel.emit(USER_DETAILS_ROUTE_PATH, TopBarNavigationState.None)
//        }
//        is TopBarNavigationState.Back -> viewModel.setEvent(UserDetailsEvent.NavigationBack)
//        else -> println("TopBarNavigationState: $action")
//    }

    when (val action = detailsAction) {
        UserDetailsActions.None -> {}
        is UserDetailsActions.ShareUrl -> {
//            onShareClick(action.url)
            viewModel.setEvent(UserDetailsEvent.None)
        }
        is UserDetailsActions.ShowError -> {
            LaunchedEffect(key1 = action) {
                onShowSnackbar(action.error.message ?: "Oops", null)
                viewModel.setEvent(UserDetailsEvent.None)
            }
        }
    }

    println("UserDetailsScreen() - state: $detailsState, $detailsAction")

    when (val state = detailsState) {
        UiState.Loading -> CircularContent()
        is UiState.Success -> {
            Column(modifier = Modifier.fillMaxSize()) {
                AppTopBar(
                    navigationIcon = AppIcons.ArrowBack,
                    title = stringResource(Res.string.feature_details_title),
                    onNavigationClick = onBackClick
                )

                UserDetailsContent(
                    isTopBarVisible = true,
                    userDetails = state.item,
                    onEventAction = viewModel::setEvent,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp)
                )
            }

        }
        else -> {}
    }}