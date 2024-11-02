package com.sample.app.core.data.model

import com.sample.app.core.model.RepositoryDetailsModel
import com.sample.app.core.network.converters.dateTimeConverter
import com.sample.app.core.network.models.NetworkRepository
import data.RepositoryDetailsEntity
import kotlinx.datetime.Instant
import kotlinx.serialization.json.Json

fun RepositoryDetailsEntity.toRepositoryDetailsModel() = RepositoryDetailsModel(
    id = repoId,
    userId = userId,
    userLogin = owner,
    avatarUrl = avatarUrl,
    name = name,
    htmlUrl = htmlUrl,
    nodeId = nodeId,
    forks = forks,
    watchersCount = watchersCount,
    createdAt = Instant.fromEpochMilliseconds(createdAt),
    updatedAt = updatedAt?.let { Instant.fromEpochMilliseconds(it) },
    pushedAt = pushedAt?.let { Instant.fromEpochMilliseconds(it) },
    defaultBranch = defaultBranch,
    stargazersCount = stargazersCount,
    description = description,
    tagsUrl = tagsUrl,
    branchesUrl = branchesUrl,
    commitsUrl = commitsUrl,
    topics = topics?.split(",") ?: emptyList(),
    license = license?.let(Json::decodeFromString),
)

fun NetworkRepository.toRepositoryDetailsModel() = RepositoryDetailsModel(
    id = id,
    userId = owner.id,
    userLogin = owner.login,
    avatarUrl = owner.avatarUrl,
    name = name,
    forks = forks,
    watchersCount = watchersCount,
    createdAt = dateTimeConverter(createdAt),
    updatedAt = dateTimeConverter(updatedAt),
    stargazersCount = stargazersCount,
    description = description,
    nodeId = nodeId,
    htmlUrl = htmlUrl,
    defaultBranch = defaultBranch,
    pushedAt = pushedAt?.let(::dateTimeConverter),
    tagsUrl = tagsUrl,
    branchesUrl = branchesUrl,
    commitsUrl = commitsUrl,
    topics = topics,
    license = networkLicense?.toLicenseModel()
)