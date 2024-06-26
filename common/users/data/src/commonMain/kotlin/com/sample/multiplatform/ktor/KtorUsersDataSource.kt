package com.sample.multiplatform.ktor

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.path

class KtorUsersDataSource(
    private val httpClient: HttpClient
) {

    suspend fun getUsers(request: KtorUsersRequest): List<KtorUsersResponse> {
        return httpClient.get {
            url {
                path("users")
                parameters.append("since", request.since.toString())
            }
        }.body()
    }
}