package com.sample.app.core.domain

import com.sample.app.core.data.repositories.recent_search.RecentSearchRepository
import com.sample.app.core.model.RecentSearchModel
import com.sample.app.core.model.RecentSearchTagsModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch


class SetRecentSearchUseCase(
    private val recentSearchRepository: RecentSearchRepository,
    private val ioScope: CoroutineScope = CoroutineScope(Dispatchers.Default)
) {

    operator fun invoke(query: String, tag: RecentSearchTagsModel = RecentSearchTagsModel.None) =
        ioScope.launch(SupervisorJob() + CoroutineExceptionHandler { coroutineContext, throwable ->
            println(throwable)
        }) {
            recentSearchRepository.insert(RecentSearchModel(query = query, tag = tag))
        }
}
