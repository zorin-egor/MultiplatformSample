package com.sample.multiplatform.ktor

import kotlinx.serialization.Serializable

@Serializable
data class KtorUsersRequest(
    val since: Long
)
