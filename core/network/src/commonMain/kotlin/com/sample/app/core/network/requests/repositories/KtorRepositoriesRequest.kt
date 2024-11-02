package com.sample.app.core.network.requests.repositories

import kotlinx.serialization.Serializable

@Serializable
data class KtorRepositoriesRequest(
    val query: String,
    val page: Long,
    val perPage: Long = 30,
    val isDescSort: Boolean? = null,
    val order: String? = null
)