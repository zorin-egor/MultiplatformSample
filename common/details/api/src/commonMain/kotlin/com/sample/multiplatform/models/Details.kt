package com.sample.multiplatform.models

data class Details(
    val id: Long,
    var nodeId: String? = null,
    var login: String? = null,
    var url: String? = null,
    var avatarUrl: String? = null
)
