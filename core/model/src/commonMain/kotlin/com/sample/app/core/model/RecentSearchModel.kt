package com.sample.app.core.model

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

data class RecentSearchModel(
    val query: String,
    val date: Instant = Clock.System.now(),
    val tag: RecentSearchTagsModel = RecentSearchTagsModel.None,
)