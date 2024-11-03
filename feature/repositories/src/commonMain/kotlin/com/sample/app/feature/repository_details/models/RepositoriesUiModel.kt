package com.sample.app.feature.repository_details.models

import com.sample.app.core.model.RepositoryModel
import com.sample.app.core.ui.widgets.SearchTextDataItem

data class RepositoriesUiModel(
    val repositories: List<RepositoryModel>,
    val isBottomProgress: Boolean
)

data class RecentSearchUiModel(
    val query: String,
    val recentSearch: List<SearchTextDataItem>,
)