package com.sample.app.core.data.model

import com.sample.app.core.model.RecentSearchModel
import com.sample.app.core.model.RecentSearchTagsModel
import data.RecentSearchEntity
import kotlinx.datetime.Instant

fun RecentSearchEntity.toRecentSearchModel() = RecentSearchModel(
    query = query,
    date = Instant.fromEpochMilliseconds(date),
    tag = RecentSearchTagsModel.valueOf(tag),
)