package com.sample.app.feature.repositories.models

import com.sample.app.core.model.RepositoryModel
import com.sample.architecturecomponents.core.designsystem.component.SearchTextDataItem

data class RepositoriesUiModel(
    val repositories: List<RepositoryModel>,
    val isBottomProgress: Boolean
)

data class RecentSearchUiModel(
    val query: String,
    val recentSearch: List<SearchTextDataItem>,
)