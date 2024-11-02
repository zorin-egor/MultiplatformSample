package com.sample.app.core.data.repositories.repositories

import app.cash.sqldelight.async.coroutines.awaitAsList
import app.cash.sqldelight.coroutines.asFlow
import com.sample.app.core.common.result.Result
import com.sample.app.core.common.result.startLoading
import com.sample.app.core.data.database.AppDatabase
import com.sample.app.core.data.model.toRepositoryModels
import com.sample.app.core.model.RepositoryModel
import com.sample.app.core.network.requests.repositories.KtorRepositoriesDataSource
import com.sample.app.core.network.requests.repositories.KtorRepositoriesRequest
import data.RepositoryEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.onStart


internal class RepositoriesRepositoryImpl(
    private val networkDatasource: KtorRepositoriesDataSource,
    private val database: AppDatabase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) : RepositoriesRepository {

    override suspend fun getRepositoriesByName(name: String, page: Long, limit: Long, isDescSort: Boolean): Flow<Result<List<RepositoryModel>>> {
        val dbOffset = (page - 1) * limit
        val dbFlow = database.db().repositoryQueries.selectRepos(name = name, offset = dbOffset, limit = limit)
            .asFlow()
            .map { it.awaitAsList() }
            .filterNot { it.isEmpty() }
            .map<List<RepositoryEntity>, Result<List<RepositoryModel>>> { Result.Success(it.toRepositoryModels()) }
            .onStart { emit(Result.Loading) }
            .catch {
                println("UsersRepositoryImpl() - $it")
                emit(Result.Error(it))
            }

        val networkFlow = flow<Result<List<RepositoryModel>>> {
            emit(Result.Loading)

            val request = KtorRepositoriesRequest(
                query = name,
                page = page,
                perPage = limit,
                isDescSort = isDescSort
            )
            val response = networkDatasource.getRepositories(request).networkRepositories.toRepositoryModels()
            if (response.isNotEmpty()) {
                runCatching {
                    response.forEach {
                        database.db().repositoryQueries.update(
                            repoId = it.id,
                            userId = it.userId,
                            owner = it.owner,
                            name = it.name,
                            avatarUrl = it.avatarUrl,
                            forks = it.forks,
                            watchersCount = it.watchersCount,
                            createdAt = it.createdAt.toEpochMilliseconds(),
                            updatedAt = it.updatedAt?.toEpochMilliseconds(),
                            stargazersCount = it.stargazersCount,
                            description = it.description,
                        )
                    }
                }.onFailure(::println)
            }

            emit(Result.Success(response))
        }.catch {
            println("UsersRepositoryImpl() - $it")
            emit(Result.Error(it))
        }

        return dbFlow.combine(networkFlow) { db, nw ->
            when {
                db is Result.Success -> db
                nw is Result.Success -> nw
                nw is Result.Error -> nw
                else -> null
            }
        }
        .mapNotNull { it }
        .startLoading()
        .distinctUntilChanged()
    }

    override suspend fun insert(item: RepositoryModel) = database.db().repositoryQueries.update(
        repoId = item.id,
        userId = item.userId,
        owner = item.owner,
        name = item.name,
        avatarUrl = item.avatarUrl,
        forks = item.forks,
        watchersCount = item.watchersCount,
        createdAt = item.createdAt.toEpochMilliseconds(),
        updatedAt = item.updatedAt?.toEpochMilliseconds(),
        stargazersCount = item.stargazersCount,
        description = item.description,
    )

    override suspend fun delete(item: RepositoryModel) = database.db().repositoryQueries.delete(item.id)

}
