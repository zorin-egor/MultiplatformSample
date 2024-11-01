package com.sample.app.core.data.repositories.repositories


//internal class RepositoriesRepositoryImpl(
//    private val networkDatasource: KtorRepositoriesDataSource,
//    private val database: AppDatabase,
//    private val ioScope: CoroutineScope
//) : RepositoriesRepository {
//
//    override fun getRepositoriesByName(name: String, page: Long, limit: Long, isDescSort: Boolean): Flow<Result<List<RepositoryModel>>> {
//        val dbOffset = (page - 1) * limit
//        val dbFlow = repositoriesDao.getRepositoriesByName(name = name, offset = dbOffset, limit = limit)
//            .take(1)
//            .filterNot { it.isEmpty() }
//            .map<List<RepositoryEntity>, Result<List<RepositoryModel>>> { Result.Success(it.asExternalModels()) }
//            .onStart { emit(Result.Loading) }
//            .catch {
//                println(it)
//                emit(Result.Error(it))
//            }
//
//        val networkFlow = flow {
//            emit(Result.Loading)
//
//            val response = networkDatasource.getRepositories(
//                name = name,
//                page = page.toInt(),
//                perPage = limit.toInt(),
//                sort = REPOSITORY_SORT_BY_NAME,
//                isDescOrder = isDescSort
//            ).getResultOrThrow().networkRepositories
//
//            if (response.isNotEmpty()) {
//                ioScope.launch {
//                    runCatching { repositoriesDao.insert(response.toRepositoryEntities()) }
//                        .onFailure(Timber::e)
//                }
//            }
//
//            emit(Result.Success(response.toRepositoryModels()))
//        }.catch {
//            println(it)
//            emit(Result.Error(it))
//        }
//
//        return networkFlow.combineLeftFirst(dbFlow)
//            .startLoading()
//            .distinctUntilChanged()
//
//        return emptyFlow()
//    }
//
//    override suspend fun insert(item: RepositoryModel) = repositoriesDao.insert(item.toRepositoryEntity())
//
//    override suspend fun delete(item: RepositoryModel) = repositoriesDao.delete(item.toRepositoryEntity())
//
//}
