package com.sample.app.core.domain

import com.sample.app.core.data.repositories.recent_search.RecentSearchRepository
import com.sample.architecturecomponents.core.di.IoScope
import com.sample.architecturecomponents.core.model.RecentSearch
import com.sample.architecturecomponents.core.model.RecentSearchTags
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


class SetRecentSearchUseCase @Inject constructor(
    private val recentSearchRepository: RecentSearchRepository,
    @IoScope private val ioScope: CoroutineScope
) {

    operator fun invoke(query: String, tag: RecentSearchTags = RecentSearchTags.None) =
        ioScope.launch(SupervisorJob() + CoroutineExceptionHandler { coroutineContext, throwable ->
            Timber.e(throwable)
        }) {
            recentSearchRepository.insert(RecentSearch(value = query, tag = tag))
        }
}
