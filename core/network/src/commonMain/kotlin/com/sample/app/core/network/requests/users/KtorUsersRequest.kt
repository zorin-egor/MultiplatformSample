package com.sample.app.core.network.requests.users

import kotlinx.serialization.Serializable

@Serializable
data class KtorUsersRequest(
    val since: Long
)
