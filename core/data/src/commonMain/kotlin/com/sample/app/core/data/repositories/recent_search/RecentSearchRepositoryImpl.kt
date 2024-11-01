package com.sample.app.core.data.repositories.recent_search


//internal class RecentSearchRepositoryImpl(
//    private val recentSearchDao: RecentSearchDao,
//    @IoScope private val ioScope: CoroutineScope
//) : RecentSearchRepository {
//
//    override fun getRecentSearch(query: String, limit: Long, tag: RecentSearchTags): Flow<Result<List<RecentSearch>>> {
//        return recentSearchDao.getRecentSearch(query = query, tag = tag, limit = limit)
//            .take(1)
//            .map { it.asExternalModels() }
//            .asResult()
//    }
//
//    override suspend fun insert(item: RecentSearch) =
//        recentSearchDao.insert(item.toRecentSearchEntity())
//
//    override suspend fun delete(item: RecentSearch) =
//        recentSearchDao.delete(item.toRecentSearchEntity())
//
//    override suspend fun delete() =
//        recentSearchDao.delete()
//
//}
