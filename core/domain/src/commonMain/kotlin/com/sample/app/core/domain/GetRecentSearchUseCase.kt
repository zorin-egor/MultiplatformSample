package com.sample.app.core.domain

import com.sample.app.core.common.result.Result
import com.sample.app.core.model.RecentSearchModel
import com.sample.app.core.model.RecentSearchTagsModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map


class GetRecentSearchUseCase(
    private val recentSearchRepository: RecentSearchRepository,
    private val dispatcher: CoroutineDispatcher
) {

    companion object {
        private const val LIMIT = 5L
    }

    operator fun invoke(query: String, tag: RecentSearchTagsModel = RecentSearchTagsModel.None): Flow<Result<List<RecentSearchModel>>> {
        if (query.isEmpty()) {
            return flowOf(Result.Success(emptyList()))
        }

        return recentSearchRepository.getRecentSearch(query = query, limit = LIMIT, tag = tag)
            .map { tags ->
                if (tags is Result.Success && tags.data.isNotEmpty()) {
                    Result.Success(tags.data.filter { it.value != query })
                } else {
                    tags
                }
            }
            .flowOn(dispatcher)
    }


}
