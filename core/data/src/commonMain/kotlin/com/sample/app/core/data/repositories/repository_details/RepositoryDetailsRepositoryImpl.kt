package com.sample.app.core.data.repositories.repository_details


//internal class RepositoryDetailsRepositoryImpl(
//    private val networkDatasource: KtorRepositoriesDataSource,
//    private val database: AppDatabase,
//    private val ioScope: CoroutineScope
//) : RepositoryDetailsRepository {
//
//    override fun getDetails(owner: String, repo: String): Flow<Result<RepositoryDetailsModel>> {
//        val dbFlow = repositoryDetailsDao.getDetailsByOwnerAndName(owner = owner, name = repo)
//            .take(1)
//            .filterNotNull()
//            .map<RepositoryDetailsEntity, Result<RepositoryDetailsModel>> { Result.Success(it.asExternalModels()) }
//            .onStart { emit(Result.Loading) }
//            .catch {
//                Timber.e(it)
//                emit(Result.Error(it))
//            }
//
//        val networkFlow = flow {
//            emit(Result.Loading)
//
//            val response = networkDatasource.getRepositoryDetails(owner = owner, repo = repo)
//                .getResultOrThrow()
//
//            ioScope.launch {
//                runCatching { repositoryDetailsDao.insert(response.toRepositoryDetailsEntity()) }
//                    .onFailure(Timber::e)
//            }
//
//            emit(Result.Success(response.toRepositoryDetailsModel()))
//        }.catch {
//            Timber.e(it)
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
//    override suspend fun insert(item: RepositoryDetailsModel) = repositoryDetailsDao.insert(item.toRepositoryDetailsEntity())
//
//    override suspend fun delete(item: RepositoryDetailsModel) = repositoryDetailsDao.delete(item.toRepositoryDetailsEntity())
//
//}
