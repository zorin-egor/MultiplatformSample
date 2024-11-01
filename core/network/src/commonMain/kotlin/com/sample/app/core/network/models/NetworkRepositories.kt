package com.sample.app.core.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkRepositories(
    @SerialName("incomplete_results") val incompleteResults: Boolean,
    @SerialName("items") val networkRepositories: List<NetworkRepository>,
    @SerialName("total_count") val totalCount: Int
)