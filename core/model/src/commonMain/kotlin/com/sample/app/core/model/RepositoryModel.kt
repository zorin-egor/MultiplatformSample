package com.sample.app.core.model

import kotlinx.datetime.Instant

data class RepositoryModel(
    val id: Long,
    val userId: Long,
    val owner: String,
    val avatarUrl: String?,
    val name: String,
    val forks: Int,
    val watchersCount: Int,
    val createdAt: Instant,
    val updatedAt: Instant,
    val stargazersCount: Int,
    val description: String?,
)
