package com.sample.app.core.network.requests.repositories

import com.sample.app.core.network.ext.SORT_ASC
import com.sample.app.core.network.ext.SORT_DESC
import com.sample.app.core.network.models.NetworkRepositories
import com.sample.app.core.network.models.NetworkRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.path

class KtorRepositoriesDataSource(
    private val httpClient: HttpClient,
) {

    suspend fun getRepositories(request: KtorRepositoriesRequest): NetworkRepositories {
        return httpClient.get {
            url {
                path("search/repositories")
                parameters.append("q", request.query)
                parameters.append("page", request.page.toString())
                parameters.append("per_page", request.perPage.toString())
                request.isDescSort?.let { parameters.append("sort", if (it) SORT_DESC else SORT_ASC) }
                request.order?.let { parameters.append("order", it) }
            }
        }.body()
    }

    suspend fun getRepositoryDetails(request: KtorRepositoryDetailsRequest): NetworkRepository {
        return httpClient.get {
            url {
                path("repos/${request.owner}/${request.repo}")
            }
        }.body()
    }
}