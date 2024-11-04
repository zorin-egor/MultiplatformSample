package com.sample.app.core.data.repositories.user_details

import app.cash.sqldelight.async.coroutines.awaitAsOneOrNull
import app.cash.sqldelight.coroutines.asFlow
import com.sample.app.core.common.result.Result
import com.sample.app.core.common.result.startLoading
import com.sample.app.core.data.database.AppDatabase
import com.sample.app.core.data.model.toUserDetailsModel
import com.sample.app.core.datastore.settings.SettingsSource
import com.sample.app.core.model.UserDetailsModel
import com.sample.app.core.network.requests.users.KtorDetailsRequest
import com.sample.app.core.network.requests.users.KtorUsersDataSource
import data.UserDetailsEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.onStart

class UserDetailsRepositoryImpl(
    private val network: KtorUsersDataSource,
    private val database: AppDatabase,
    private val settings: SettingsSource,
    private val scope: CoroutineScope
) : UserDetailsRepository {

    override suspend fun getUserDetails(id: Long, url: String): Flow<Result<UserDetailsModel>> {
        val dbFlow = database.db().user_detailsQueries.selectById(id = id)
            .asFlow()
            .mapNotNull { it.awaitAsOneOrNull() }
            .map<UserDetailsEntity, Result<UserDetailsModel>> { Result.Success(it.toUserDetailsModel()) }
            .onStart { emit(Result.Loading) }
            .catch {
                println("UserDetailsRepositoryImpl() - $it")
                emit(Result.Error(it))
            }

        val nwFlow = flow<Result<UserDetailsModel>> {
            emit(Result.Loading)

            val result = network.getDetails(KtorDetailsRequest(url))

            runCatching {
                database.db().user_detailsQueries.update(
                    idInner = result.id,
                    name = result.name,
                    login = result.login,
                    avatarUrl = result.avatarUrl,
                    url = result.url,
                    reposUrls = result.reposUrl,
                    company = result.company,
                    blog = result.blog,
                    location = result.location,
                    email = result.email,
                    hireable = result.hireable?.toString(),
                    bio = result.bio,
                    publicRepos = result.publicRepos,
                    publicGists = result.publicGists,
                    followers = result.followers,
                    following = result.following,
                    createdAt = result.createdAt,
                    updatedAt = result.updatedAt
                )
            }

            emit(Result.Success(result.toUserDetailsModel()))

        }.catch {
            println("UserDetailsRepositoryImpl() - $it")
            emit(Result.Error(it))
        }

        return dbFlow.combine(nwFlow) { db, nw ->
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