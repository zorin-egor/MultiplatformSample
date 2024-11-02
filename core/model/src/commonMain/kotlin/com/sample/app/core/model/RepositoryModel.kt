package com.sample.app.core.model

import kotlinx.datetime.Instant


data class RepositoryModel(
    val id: Long,
    val userId: Long,
    val owner: String,
    val avatarUrl: String?,
    val name: String,
    val forks: Long,
    val watchersCount: Long,
    val createdAt: Instant,
    val updatedAt: Instant?,
    val stargazersCount: Long,
    val description: String?,
)
