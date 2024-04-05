package com.sample.multiplatform

import com.sample.multiplatform.db.mapTo
import com.sample.multiplatform.ktor.KtorUsersDataSource
import com.sample.multiplatform.ktor.KtorUsersRequest
import com.sample.multiplatform.ktor.mapTo
import com.sample.multiplatform.models.UserModel
import com.sample.multiplatform.settings.SettingsUsersSource
import com.sample.multiplatform.users.data.Database
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

internal class UsersRepositoryImpl(
    private val database: Database,
    private val remoteDataSource: KtorUsersDataSource,
    private val cacheDataSource: SettingsUsersSource,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : UsersRepository {

    override suspend fun getUsers(since: Long, isUseOnlyCache: Boolean): List<UserModel> {
        val users = withContext(context = dispatcher) {
            val dbAwait = async {
                runCatching {
                    database.usersQueries.selectSince(count = 30, offset = since)
                        .executeAsList()
                        .map(::mapTo)
                }
            }

            val requestAwait = if (!isUseOnlyCache) {
                async {
                    runCatching {
                        remoteDataSource.getUsers(KtorUsersRequest(since)).map(::mapTo)
                    }
                }
            } else {
                null
            }

            val dbResult = dbAwait.await()
            val dbData = dbResult.getOrNull()
            val requestResult = requestAwait?.await()
            val requestData = requestResult?.getOrNull()

            when {
                !isUseOnlyCache && requestResult?.isSuccess == true && requestData != null ->
                    requestData.onEach {
                        database.usersQueries.update(
                            idInner = it.id,
                            login = it.login,
                            avatarUrl = it.avatarUrl,
                            url = it.url,
                            reposUrls = it.reposUrl,
                            followersUrl = it.followersUrl,
                            subscriptionsUrl = it.subscriptionsUrl
                        )
                    }
                dbResult.isSuccess && dbData != null -> dbData
                else -> throw (requestResult?.exceptionOrNull() ?: dbResult.exceptionOrNull()
                    ?: IllegalStateException("No result data"))
            }
        }

        cacheDataSource.saveSince(since + users.size)
        return users
    }

}