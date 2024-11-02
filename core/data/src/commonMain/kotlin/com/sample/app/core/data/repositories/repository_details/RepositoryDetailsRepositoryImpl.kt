package com.sample.app.core.data.repositories.repository_details

import app.cash.sqldelight.async.coroutines.awaitAsOneOrNull
import app.cash.sqldelight.coroutines.asFlow
import com.sample.app.core.common.result.Result
import com.sample.app.core.common.result.combineLeftFirst
import com.sample.app.core.common.result.startLoading
import com.sample.app.core.data.database.AppDatabase
import com.sample.app.core.data.model.toRepositoryDetailsModel
import com.sample.app.core.model.RepositoryDetailsModel
import com.sample.app.core.network.requests.repositories.KtorRepositoriesDataSource
import com.sample.app.core.network.requests.repositories.KtorRepositoryDetailsRequest
import data.RepositoryDetailsEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.onStart
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


internal class RepositoryDetailsRepositoryImpl(
    private val networkDatasource: KtorRepositoriesDataSource,
    private val database: AppDatabase,
) : RepositoryDetailsRepository {

    override suspend fun getDetails(owner: String, repo: String): Flow<Result<RepositoryDetailsModel>> {
        val dbFlow = database.db().repository_detailsQueries.selectByOwnerAndName(owner = owner, name = repo)
            .asFlow()
            .mapNotNull { it.awaitAsOneOrNull() }
            .map<RepositoryDetailsEntity, Result<RepositoryDetailsModel>> { Result.Success(it.toRepositoryDetailsModel()) }
            .onStart { emit(Result.Loading) }
            .catch {
                println(it)
                emit(Result.Error(it))
            }

        val networkFlow = flow {
            emit(Result.Loading)

            val request = KtorRepositoryDetailsRequest(
                owner = owner,
                repo = repo
            )
            
            val response = networkDatasource.getRepositoryDetails(request).toRepositoryDetailsModel()

            runCatching {
                database.db().repository_detailsQueries.update(
                    repoId = response.id,
                    userId = response.userId,
                    owner = response.userLogin,
                    name = response.name,
                    avatarUrl = response.avatarUrl,
                    forks = response.forks,
                    watchersCount = response.watchersCount,
                    createdAt = response.createdAt.toEpochMilliseconds(),
                    updatedAt = response.updatedAt?.toEpochMilliseconds(),
                    stargazersCount = response.stargazersCount,
                    description = response.description,
                    htmlUrl = response.htmlUrl,
                    nodeId = response.nodeId,
                    pushedAt = response.pushedAt?.toEpochMilliseconds(),
                    defaultBranch = response.defaultBranch,
                    tagsUrl = response.tagsUrl,
                    branchesUrl = response.branchesUrl,
                    commitsUrl = response.commitsUrl,
                    topics = response.topics.joinToString(separator = ",") { it },
                    license = response.license?.let(Json::encodeToString),
                )
            }.onFailure(::println)
            
            emit(Result.Success(response))
        }.catch {
            println(it)
            emit(Result.Error(it))
        }

        return networkFlow.combineLeftFirst(dbFlow)
            .startLoading()
            .distinctUntilChanged()
    }

    override suspend fun insert(item: RepositoryDetailsModel) = database.db().repository_detailsQueries.update(
        repoId = item.id,
        userId = item.userId,
        owner = item.userLogin,
        name = item.name,
        avatarUrl = item.avatarUrl,
        forks = item.forks,
        watchersCount = item.watchersCount,
        createdAt = item.createdAt.toEpochMilliseconds(),
        updatedAt = item.updatedAt?.toEpochMilliseconds(),
        stargazersCount = item.stargazersCount,
        description = item.description,
        htmlUrl = item.htmlUrl,
        nodeId = item.nodeId,
        pushedAt = item.pushedAt?.toEpochMilliseconds(),
        defaultBranch = item.defaultBranch,
        tagsUrl = item.tagsUrl,
        branchesUrl = item.branchesUrl,
        commitsUrl = item.commitsUrl,
        topics = item.topics.joinToString(separator = ",") { it },
        license = item.license?.let(Json::encodeToString),
    )

    override suspend fun delete(item: RepositoryDetailsModel) =
        database.db().repository_detailsQueries.delete(item.id)

}
