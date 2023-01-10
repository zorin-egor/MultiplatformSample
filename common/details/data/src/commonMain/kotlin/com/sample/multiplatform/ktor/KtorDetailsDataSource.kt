package com.sample.multiplatform.ktor

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class KtorDetailsDataSource(
    private val httpClient: HttpClient
) {

    suspend fun getDetails(request: KtorDetailsRequest): KtorDetailsResponse {
        return httpClient.get(request.url).body()
    }
}