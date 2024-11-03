package com.sample.app.core.model

import kotlinx.datetime.Instant

data class RepositoryDetailsModel(
    val id: Long,
    val userId: Long,
    val userLogin: String,
    val avatarUrl: String?,
    val name: String,
    val htmlUrl: String,
    val nodeId: String,
    val forks: Long,
    val watchersCount: Long,
    val createdAt: Instant,
    val updatedAt: Instant?,
    val pushedAt: Instant?,
    val defaultBranch: String,
    val stargazersCount: Long,
    val description: String?,
    val tagsUrl: String?,
    val branchesUrl: String,
    val commitsUrl: String?,
    val topics: List<String>,
    val license: LicenseModel?
)