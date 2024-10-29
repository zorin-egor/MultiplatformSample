package com.sample.app.core.data.repositories.users

import app.cash.sqldelight.async.coroutines.awaitAsList
import com.sample.app.common.result.Result
import com.sample.app.core.data.database.AppDatabase
import com.sample.app.core.data.model.mapTo
import com.sample.app.core.datastore.settings.SettingsSource
import com.sample.app.core.model.UserModel
import com.sample.app.core.network.requests.users.KtorUsersDataSource
import com.sample.app.core.network.requests.users.KtorUsersRequest
import com.sample.app.core.network.requests.users.mapTo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart

internal class UsersRepositoryImpl(
    private val network: KtorUsersDataSource,
    private val database: AppDatabase,
    private val settings: SettingsSource,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) : UsersRepository {

    override fun getUsers(sinceId: Long, lastId: Long, limit: Long): Flow<Result<List<UserModel>>> =
        flow<Result<List<UserModel>>> {
            val db = database.db().usersQueries.selectSinceId(sinceId = sinceId, count = 30)
                .awaitAsList()
                .map(::mapTo)

            val newSize1 = database.db().usersQueries.countAll().awaitAsList()
            println("db size before: ${newSize1.firstOrNull()}")

            println("db size before: ${db.size}")
            if (db.isNotEmpty()) {
                println("Load from db")
                emit(Result.Success(db))
                return@flow
            }

            println("Load from nw")
            val nw = network.getUsers(KtorUsersRequest(sinceId)).map(::mapTo)

            println("nw size after: ${nw.size}")
            settings.setSinceUser(nw.lastOrNull()?.id ?: sinceId)
            nw.forEach {
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

            val newSize = database.db().usersQueries.countAll().awaitAsList()
            println("db size after: ${newSize.firstOrNull()}")

            emit(Result.Success(nw))
        }
        .onStart { Result.Loading }
        .catch {
            println("UsersRepositoryImpl() - $it")
            emit(Result.Error(it))
        }
}