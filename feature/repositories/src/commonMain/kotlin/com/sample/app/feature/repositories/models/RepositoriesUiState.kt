package com.sample.app.feature.repositories.models

import com.sample.app.core.ui.viewmodels.UiState


data class RepositoriesUiState(
    val searchState: UiState<RecentSearchUiModel>,
    val repoState: UiState<RepositoriesUiModel>
)

val emptyRepositoriesUiState = RepositoriesUiState(
    searchState = UiState.Empty,
    repoState = UiState.Empty
)