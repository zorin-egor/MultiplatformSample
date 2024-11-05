package com.sample.app

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sample.app.core.ui.components.AppTopBar
import com.sample.app.core.ui.icon.AppIcons
import com.sample.app.core.ui.resources.core_common_settings
import com.sample.app.core.ui.resources.core_common_share
import com.sample.app.core.ui.viewmodels.TopBarNavigationState
import com.sample.app.core.ui.viewmodels.TopBarNavigationViewModel
import com.sample.app.feature.repository_details.navigation.REPOSITORY_DETAILS_ROUTE_PATH
import com.sample.app.feature.repository_details.resources.feature_repository_details_title
import com.sample.app.feature.user_details.navigation.USER_DETAILS_ROUTE_PATH
import com.sample.app.feature.user_details.resources.feature_user_details_title
import com.sample.app.feature.users.navigation.USERS_ROUTE
import com.sample.app.feature.users.resources.Res
import com.sample.app.feature.users.resources.feature_users_title
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

import com.sample.app.core.ui.resources.Res as CoreUiRes
import com.sample.app.feature.repository_details.resources.Res as FeatureRepoDetailsRes
import com.sample.app.feature.user_details.resources.Res as FeatureUserDetailsRes
import com.sample.app.feature.users.resources.Res as FeatureUsersRes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun NavAppTopBar(
    state: AppState,
) {
    val viewModel: TopBarNavigationViewModel = viewModel { TopBarNavigationViewModel() }

    var isTopBarVisible = false
    var toolbarTitle: StringResource? = null
    var navigationIcon: ImageVector? = null
    var navigationDesc: String? = null
    var actionIcon: ImageVector? = null
    var actionDesc: String? = null
    var actionClick: () -> Unit = {}
    val onBackClick: () -> Unit = { state.navController.navigateUp() }
    val route = state.routeState.value
    
    println("NavAppTopBar() - $route, ${viewModel.hashCode()}")

    when (route) {
        USERS_ROUTE -> {
            toolbarTitle = Res.string.feature_users_title
            actionIcon = AppIcons.Settings
            actionDesc = stringResource(resource = CoreUiRes.string.core_common_settings)
            actionClick = { viewModel.emit(route, TopBarNavigationState.Menu) }
            isTopBarVisible = state.shouldShowNavRail
        }
    }

    if (isTopBarVisible) {
        AppTopBar(
            title = toolbarTitle?.let { stringResource(it) },
            navigationIcon = navigationIcon,
            navigationIconContentDescription = navigationDesc,
            actionIcon = actionIcon,
            actionIconContentDescription = actionDesc,
            onActionClick = actionClick,
            onNavigationClick = onBackClick,
            modifier = Modifier.fillMaxWidth().wrapContentHeight()
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun UserDetailsTopAppBar(
    onBackClick: () -> Unit,
    viewModel: TopBarNavigationViewModel = viewModel { TopBarNavigationViewModel() }
) {
    AppTopBar(
        title = stringResource(FeatureUserDetailsRes.string.feature_user_details_title),
        navigationIcon = AppIcons.ArrowBack,
        navigationIconContentDescription = stringResource(FeatureUserDetailsRes.string.feature_user_details_title),
        actionIcon = AppIcons.Share,
        actionIconContentDescription = stringResource(resource = CoreUiRes.string.core_common_share),
        onActionClick = { viewModel.emit(USER_DETAILS_ROUTE_PATH, TopBarNavigationState.Menu) },
        onNavigationClick = onBackClick,
        modifier = Modifier.fillMaxWidth().wrapContentHeight()
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun RepositoryDetailsTopAppBar(
    onBackClick: () -> Unit,
    viewModel: TopBarNavigationViewModel = viewModel { TopBarNavigationViewModel() }
) {
    AppTopBar(
        title = stringResource(FeatureRepoDetailsRes.string.feature_repository_details_title),
        navigationIcon = AppIcons.ArrowBack,
        navigationIconContentDescription = stringResource(FeatureRepoDetailsRes.string.feature_repository_details_title),
        actionIcon = AppIcons.Share,
        actionIconContentDescription = stringResource(resource = CoreUiRes.string.core_common_share),
        onActionClick = { viewModel.emit(REPOSITORY_DETAILS_ROUTE_PATH, TopBarNavigationState.Menu) },
        onNavigationClick = onBackClick,
        modifier = Modifier.fillMaxWidth().wrapContentHeight()
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun UsersTopAppBar(
    onBackClick: () -> Unit,
    viewModel: TopBarNavigationViewModel = viewModel { TopBarNavigationViewModel() }
) {
    AppTopBar(
        title = stringResource(FeatureUsersRes.string.feature_users_title),
        actionIcon = AppIcons.Settings,
        actionIconContentDescription = stringResource(resource = CoreUiRes.string.core_common_share),
        onActionClick = { viewModel.emit(REPOSITORY_DETAILS_ROUTE_PATH, TopBarNavigationState.Menu) },
        modifier = Modifier.fillMaxWidth().wrapContentHeight()
    )
}
