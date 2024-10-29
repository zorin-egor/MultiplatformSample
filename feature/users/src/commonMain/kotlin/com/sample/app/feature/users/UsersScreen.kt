package com.sample.app.feature.users

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sample.app.core.model.UserModel
import com.sample.app.core.ui.icon.AppIcons
import com.sample.app.core.ui.viewmodels.UiState
import com.sample.app.core.ui.widgets.CircularContent
import com.sample.app.core.ui.widgets.ListContentWidget
import com.sample.app.core.ui.widgets.SimplePlaceholderContent
import com.sample.app.feature.users.models.UsersActions
import com.sample.app.feature.users.models.UsersEvents
import com.sample.app.feature.users.widgets.UsersItemContent


@Composable
fun UsersScreen(
    onUserClick: (Long, String) -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    viewModel: UsersViewModel = viewModel { UsersViewModel() }
) {
    println("UsersScreen()")

    val usersUiState by viewModel.state.collectAsStateWithLifecycle()
    val usersAction by viewModel.action.collectAsStateWithLifecycle()
    val nextUsers: () -> Unit = remember {{ viewModel.setEvent(UsersEvents.NextUser) }}

    println("UsersScreen() - state: $usersUiState, $usersAction")

    when(val state = usersUiState) {
        UiState.Loading -> CircularContent()

        UiState.Empty -> SimplePlaceholderContent(
            header = "Empty",
            title = "Title",
            image = AppIcons.Search,
            imageContentDescription = "contentDescription"
        )

        is UiState.Success -> {
            ListContentWidget<UserModel>(
                items = state.item.users,
                onKey = { it.id.toString() },
                onBottomEvent = nextUsers,
                isBottomProgress = state.item.isBottomProgress
            ) { user ->
                UsersItemContent(
                    user = user,
                    onEventAction = viewModel::setEvent,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                )
            }
        }

        else -> {}
    }

    when(val action = usersAction) {
        UsersActions.None -> {}

        is UsersActions.NavigateToDetails -> {
            onUserClick(action.id, action.url)
            viewModel.setEvent(UsersEvents.None)
        }

        is UsersActions.ShowError -> {
            println("User action: $action")
            LaunchedEffect(key1 = action) {
                onShowSnackbar(action.error.message ?: "Oops", null)
                viewModel.setEvent(UsersEvents.None)
            }
        }

    }
}

