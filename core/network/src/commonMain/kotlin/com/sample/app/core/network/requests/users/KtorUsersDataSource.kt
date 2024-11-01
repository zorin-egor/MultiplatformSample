package com.sample.app.core.network.requests.users

import com.sample.app.core.datastore.settings.SettingsSource
import com.sample.app.core.network.models.NetworkUser
import com.sample.app.core.network.models.NetworkUserDetails
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.path

class KtorUsersDataSource(
    private val httpClient: HttpClient,
    private val settingsSource: SettingsSource
) {

    suspend fun getUsers(request: KtorUsersRequest): List<NetworkUser> {
        val token = settingsSource.getToken()
        return httpClient.get {
            url {
                path("users")
                parameters.append("since", request.since.toString())
            }
        }.body()
    }

    suspend fun getDetails(request: KtorDetailsRequest): NetworkUserDetails {
        return httpClient.get(request.url).body()
    }
}