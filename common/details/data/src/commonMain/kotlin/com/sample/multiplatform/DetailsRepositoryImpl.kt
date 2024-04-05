package com.sample.multiplatform

import com.sample.multiplatform.db.mapTo
import com.sample.multiplatform.details.data.Database
import com.sample.multiplatform.ktor.KtorDetailsDataSource
import com.sample.multiplatform.ktor.KtorDetailsRequest
import com.sample.multiplatform.ktor.mapTo
import com.sample.multiplatform.models.DetailsModel
import com.sample.multiplatform.settings.SettingsDetailsSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class DetailsRepositoryImpl(
    private val database: Database,
    private val remoteDataSource: KtorDetailsDataSource,
    private val cacheDataSource: SettingsDetailsSource,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : DetailsRepository {

    override suspend fun getUserDetails(id: Long, url: String): DetailsModel {
        return withContext(context = dispatcher) {
            val dbAwait = async {
                runCatching {
                    database.detailsQueries.selectById(id = id).executeAsOne().let(::mapTo)
                }
            }

            val requestAwait = async {
                runCatching {
                    remoteDataSource.getDetails(KtorDetailsRequest(url)).let(::mapTo)
                }
            }

            val dbResult = dbAwait.await()
            val dbData = dbResult.getOrNull()
            val requestResult = requestAwait.await()
            val requestData = requestResult.getOrNull()

            when {
                requestResult.isSuccess && requestData != null -> requestData.also {
                    database.detailsQueries.update(
                        idInner = it.id,
                        nodeId = it.nodeId,
                        login = it.login,
                        avatarUrl = it.avatarUrl,
                        url = it.url,
                        reposUrls = it.reposUrl,
                        followersUrl = it.followersUrl,
                        subscriptionsUrl = it.subscriptionsUrl,
                        followingUrl = it.followingUrl,
                        gistsUrl = it.gistsUrl,
                        starredUrl = it.starredUrl,
                        organizationsUrl = it.organizationsUrl,
                        eventsUrl = it.eventsUrl,
                        receivedEventsUrl = it.receivedEventsUrl,
                        company = it.company,
                        blog = it.blog,
                        location = it.location,
                        email = it.email,
                        hireable = it.hireable,
                        bio = it.bio,
                        publicRepos = it.publicRepos,
                        publicGists = it.publicGists,
                        followers = it.followers,
                        following = it.following,
                        createdAt = it.createdAt,
                        updatedAt = it.updatedAt
                    )
                }
                dbResult.isSuccess && dbData != null -> dbData
                else -> throw (requestResult.exceptionOrNull() ?: dbResult.exceptionOrNull()
                    ?: IllegalStateException("No result data"))
            }
        }
    }

}