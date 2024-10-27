package com.sample.app.core.network.requests.users

import com.sample.app.core.datastore.settings.SettingsSource
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.path

class KtorUsersDataSource(
    private val httpClient: HttpClient,
    private val settingsSource: SettingsSource
) {

    suspend fun getUsers(request: KtorUsersRequest): List<KtorUsersResponse> {
        val token = settingsSource.getToken()
        return httpClient.get {
            url {
                path("users")
                parameters.append("since", request.since.toString())
            }
        }.body()
    }
}