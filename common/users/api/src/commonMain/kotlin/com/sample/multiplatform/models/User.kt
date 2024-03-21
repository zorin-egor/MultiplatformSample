package com.sample.multiplatform.models

data class User(
    val id: Long,
    var login: String,
    var url: String? = null,
    var avatarUrl: String? = null,
    val reposUrl: String? = null,
    val followersUrl: String? = null,
    val subscriptionsUrl: String? = null,
)