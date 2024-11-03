package com.sample.app.core.data.model

import com.sample.app.core.model.LicenseModel
import com.sample.app.core.model.RepositoryModel
import com.sample.app.core.model.converters.dateTimeConverter
import com.sample.app.core.network.models.NetworkLicense
import com.sample.app.core.network.models.NetworkRepository
import data.RepositoryEntity
import kotlinx.datetime.Instant

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

fun List<NetworkRepository>.networkToRepositoryModels() = map { it.toRepositoryModel() }

fun RepositoryModel.toRepositoryEntity() = RepositoryEntity(
    id = 0,
    repoId = id,
    userId = userId,
    owner = owner,
    avatarUrl = avatarUrl,
    name = name,
    forks = forks,
    watchersCount = watchersCount,
    createdAt = createdAt.toEpochMilliseconds(),
    updatedAt = updatedAt?.toEpochMilliseconds(),
    stargazersCount = stargazersCount,
    description = description,
)                   

fun RepositoryEntity.toRepositoryModel() = RepositoryModel(
    id = repoId,
    userId = userId,
    owner = owner,
    name = name,
    avatarUrl = avatarUrl,
    forks = forks,
    watchersCount = watchersCount,
    createdAt = Instant.fromEpochMilliseconds(createdAt),
    updatedAt = updatedAt?.let { Instant.fromEpochMilliseconds(it) },
    stargazersCount = stargazersCount,
    description = description,
)

fun List<RepositoryEntity>.entitiesToRepositoryModels() = map { it.toRepositoryModel() }























