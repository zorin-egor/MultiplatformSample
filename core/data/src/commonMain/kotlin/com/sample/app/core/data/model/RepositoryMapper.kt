package com.sample.app.core.data.model

import com.sample.app.core.model.LicenseModel
import com.sample.app.core.model.RepositoryDetailsModel
import com.sample.app.core.model.RepositoryModel
import com.sample.app.core.network.converters.dateTimeConverter
import com.sample.app.core.network.models.NetworkLicense
import com.sample.app.core.network.models.NetworkRepository

fun NetworkRepository.toRepositoryModel() = RepositoryModel(
    id = id,
    userId = owner.id,
    owner = owner.login,
    avatarUrl = owner.avatarUrl,
    name = name,
    forks = forks,
    watchersCount = watchersCount,
    createdAt = dateTimeConverter(createdAt),
    updatedAt = dateTimeConverter(updatedAt),
    stargazersCount = stargazersCount,
    description = description,
)

fun NetworkLicense.toLicenseModel() = LicenseModel(
    key = key,
    url = url
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