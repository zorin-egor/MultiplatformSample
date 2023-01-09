package com.sample.multiplatform.ktor

import com.sample.multiplatform.models.User
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.path

class KtorUsersDataSource(
    private val httpClient: HttpClient
) {

    suspend fun getUsers(request: KtorUsersRequest): List<User> {
        return httpClient.get {
            url {
                path("users")
                setAttributes {
                    "since" to request.since
                }
            }
        }.body()
    }
}