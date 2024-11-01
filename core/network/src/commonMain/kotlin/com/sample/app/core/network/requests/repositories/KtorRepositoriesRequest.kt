package com.sample.app.core.network.requests.repositories

import kotlinx.serialization.Serializable

@Serializable
data class KtorRepositoriesRequest(
    val query: String,
    val page: Int,
    val perPage: Int = 30,
    val sort: String? = null,
    val order: String? = null
)