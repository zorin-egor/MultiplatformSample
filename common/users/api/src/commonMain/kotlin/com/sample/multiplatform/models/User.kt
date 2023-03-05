package com.sample.multiplatform.models

data class User(
    val id: Long,
    var login: String? = null,
    var url: String? = null,
    var avatarUrl: String? = null
)