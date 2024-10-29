package com.sample.app.core.data.repositories.details

import com.sample.app.common.result.Result
import com.sample.app.core.data.database.AppDatabase
import com.sample.app.core.datastore.settings.SettingsSource
import com.sample.app.core.model.DetailsModel
import com.sample.app.core.network.requests.details.KtorDetailsDataSource
import com.sample.app.core.network.requests.details.KtorDetailsRequest
import com.sample.app.core.network.requests.details.mapTo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart

class DetailsRepositoryImpl(
    private val network: KtorDetailsDataSource,
    private val database: AppDatabase,
    private val settings: SettingsSource,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) : DetailsRepository {

    override fun getUserDetails(id: Long, url: String): Flow<Result<DetailsModel>> =
        flow<Result<DetailsModel>> {
            val result = network.getDetails(KtorDetailsRequest(url))

            database.db().detailsQueries.update(
                idInner = result.id,
                nodeId = result.nodeId,
                login = result.login,
                avatarUrl = result.avatarUrl,
                url = result.url,
                reposUrls = result.reposUrl,
                followersUrl = result.followersUrl,
                subscriptionsUrl = result.subscriptionsUrl,
                followingUrl = result.followingUrl,
                gistsUrl = result.gistsUrl,
                starredUrl = result.starredUrl,
                organizationsUrl = result.organizationsUrl,
                eventsUrl = result.eventsUrl,
                receivedEventsUrl = result.receivedEventsUrl,
                company = result.company,
                blog = result.blog,
                location = result.location,
                email = result.email,
                hireable = result.hireable,
                bio = result.bio,
                publicRepos = result.publicRepos,
                publicGists = result.publicGists,
                followers = result.followers,
                following = result.following,
                createdAt = result.createdAt,
                updatedAt = result.updatedAt
            )

            emit(Result.Success(mapTo(result)))
        }
        .onStart { emit(Result.Loading) }
        .catch {
            println("DetailsRepositoryImpl() - $it")
            emit(Result.Error(it))
        }

}