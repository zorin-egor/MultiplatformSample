package com.sample.app.core.data.repositories.recent_search

import app.cash.sqldelight.async.coroutines.awaitAsList
import app.cash.sqldelight.coroutines.asFlow
import com.sample.app.core.common.result.Result
import com.sample.app.core.common.result.asResult
import com.sample.app.core.data.database.AppDatabase
import com.sample.app.core.data.model.toRecentSearchModels
import com.sample.app.core.model.RecentSearchModel
import com.sample.app.core.model.RecentSearchTagsModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.take


internal class RecentSearchRepositoryImpl(
    private val database: AppDatabase,
) : RecentSearchRepository {

    override suspend fun getRecentSearch(query: String, limit: Long, tag: RecentSearchTagsModel): Flow<Result<List<RecentSearchModel>>> {
        return database.db().recent_searchQueries.selectRecentSearch(query = query, tag = tag.name, limit = limit)
            .asFlow()
            .take(1)
            .map { it.awaitAsList().toRecentSearchModels() }
            .asResult()
    }

    override suspend fun insert(item: RecentSearchModel) =
        database.db().recent_searchQueries.update(
            query = item.query,
            date = item.date.toEpochMilliseconds(),
            tag = item.tag.name
        )

    override suspend fun delete(item: RecentSearchModel) =
        database.db().recent_searchQueries.delete(
            query = item.query,
            tag = item.tag.name
        )

    override suspend fun delete() =
        database.db().recent_searchQueries.deleteAll()

}
