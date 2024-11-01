package com.sample.app.core.data.repositories.user_details

import app.cash.sqldelight.async.coroutines.awaitAsOneOrNull
import com.sample.app.core.common.result.Result
import com.sample.app.core.data.database.AppDatabase
import com.sample.app.core.data.model.toUserDetailsModel
import com.sample.app.core.datastore.settings.SettingsSource
import com.sample.app.core.model.UserDetailsModel
import com.sample.app.core.network.requests.users.KtorDetailsRequest
import com.sample.app.core.network.requests.users.KtorUsersDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart

class UserDetailsRepositoryImpl(
    private val network: KtorUsersDataSource,
    private val database: AppDatabase,
    private val settings: SettingsSource,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) : UserDetailsRepository {

    override fun getUserDetails(id: Long, url: String): Flow<Result<UserDetailsModel>> =
        flow<Result<UserDetailsModel>> {
            val db = database.db().user_detailsQueries.selectById(id).awaitAsOneOrNull()
            if (db != null) {
                emit(Result.Success(db.toUserDetailsModel()))
                return@flow
            }

            val result = network.getDetails(KtorDetailsRequest(url))

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

            emit(Result.Success(result.toUserDetailsModel()))
        }
        .onStart { emit(Result.Loading) }
        .catch {
            println("DetailsRepositoryImpl() - $it")
            emit(Result.Error(it))
        }

}