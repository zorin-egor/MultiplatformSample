package com.sample.app.core.model

data class UserModel(
    val id: Long,
    var login: String,
    var url: String,
    var avatarUrl: String? = null,
    val reposUrl: String? = null,
    val followersUrl: String? = null,
    val subscriptionsUrl: String? = null,
)