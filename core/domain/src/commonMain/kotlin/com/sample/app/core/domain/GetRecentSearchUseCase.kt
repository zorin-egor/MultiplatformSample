package com.sample.app.core.domain

import com.sample.app.core.common.result.Result
import com.sample.app.core.data.repositories.recent_search.RecentSearchRepository
import com.sample.app.core.model.RecentSearchModel
import com.sample.app.core.model.RecentSearchTagsModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class GetRecentSearchUseCase(
    private val recentSearchRepository: RecentSearchRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) {

    companion object {
        private const val LIMIT = 5L
    }

    suspend operator fun invoke(query: String, tag: RecentSearchTagsModel = RecentSearchTagsModel.None): Result<List<RecentSearchModel>> {
        if (query.isEmpty()) {
            return Result.Success(emptyList())
        }

        return withContext(dispatcher) {
            val result = recentSearchRepository.getRecentSearch(query = query, limit = LIMIT, tag = tag)
            if (result is Result.Success && result.data.isNotEmpty()) {
                Result.Success(result.data.filter { it.query != query })
            } else {
                result
            }
        }
    }


}
