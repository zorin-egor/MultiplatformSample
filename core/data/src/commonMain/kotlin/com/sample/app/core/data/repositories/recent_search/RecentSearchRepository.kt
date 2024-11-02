package com.sample.app.core.data.repositories.recent_search

import com.sample.app.core.common.result.Result
import com.sample.app.core.model.RecentSearchModel
import com.sample.app.core.model.RecentSearchTagsModel

interface RecentSearchRepository {

    suspend fun getRecentSearch(query: String, limit: Long = 10, tag: RecentSearchTagsModel = RecentSearchTagsModel.None): Result<List<RecentSearchModel>>

    suspend fun insert(item: RecentSearchModel)

    suspend fun delete(item: RecentSearchModel)

    suspend fun delete()

}