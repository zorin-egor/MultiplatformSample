package com.sample.app.core.data.repositories.users

import app.cash.sqldelight.async.coroutines.awaitAsList
import app.cash.sqldelight.coroutines.asFlow
import com.sample.app.common.result.Result
import com.sample.app.common.result.startLoading
import com.sample.app.core.data.database.AppDatabase
import com.sample.app.core.data.model.mapTo
import com.sample.app.core.datastore.settings.SettingsSource
import com.sample.app.core.model.UserModel
import com.sample.app.core.network.requests.users.KtorUsersDataSource
import com.sample.app.core.network.requests.users.KtorUsersRequest
import com.sample.app.core.network.requests.users.mapTo
import data.UsersEntity
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

internal class UsersRepositoryImpl(
    private val network: KtorUsersDataSource,
    private val database: AppDatabase,
    private val settings: SettingsSource,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) : UsersRepository {

    override suspend fun getUsers(sinceId: Long, limit: Long): Flow<Result<List<UserModel>>> {
        val dbFlow = database.db().usersQueries.selectSinceId(sinceId = sinceId, count = limit)
            .asFlow()
            .map { it.awaitAsList() }
            .filterNot { it.isEmpty() }
            .map<List<UsersEntity>, Result<List<UserModel>>> { Result.Success(mapTo(it)) }
            .onStart { emit(Result.Loading) }
            .catch {
                println("UsersRepositoryImpl() - $it")
                emit(Result.Error(it))
            }

        val networkFlow = flow<Result<List<UserModel>>> {
            emit(Result.Loading)

            val response = network.getUsers(KtorUsersRequest(sinceId)).map(::mapTo)
            if (response.isNotEmpty()) {
                runCatching {
                    response.forEach {
                        database.db().usersQueries.update(
                            idInner = it.id,
                            login = it.login,
                            avatarUrl = it.avatarUrl,
                            url = it.url,
                            reposUrls = it.reposUrl,
                            followersUrl = it.followersUrl,
                            subscriptionsUrl = it.subscriptionsUrl
                        )
                    }
                }.onFailure(::println)
            }

            response.lastOrNull()?.id?.let(settings::setSinceUser)
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

}