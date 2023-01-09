package com.sample.multiplatform.models

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: Long,
    var updateTime: Long = 0,
    var userId: Long = -1,
    var nodeId: String? = null,
    var login: String? = null,
    var url: String? = null,
    var avatarUrl: String? = null
)