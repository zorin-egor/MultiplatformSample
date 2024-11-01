package com.sample.app.core.network.requests.repositories

import kotlinx.serialization.Serializable

@Serializable
data class KtorRepositoryDetailsRequest(
    val owner: String,
    val repo: String
)