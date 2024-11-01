package com.sample.app.core.data.model

import com.sample.app.core.model.UserModel
import com.sample.app.core.network.models.NetworkUser
import data.UsersEntity

fun UsersEntity.toUserModel() = UserModel(
    id = idInner,
    login = login,
    url = url,
    avatarUrl = avatarUrl,
    reposUrl = reposUrls,
    followersUrl = followersUrl,
    subscriptionsUrl = subscriptionsUrl
)

fun List<UsersEntity>.entitiesToUserModels() = map { it.toUserModel() }

fun NetworkUser.toUserModel() = UserModel(
    id = id,
    login = login,
    url = url,
    avatarUrl = avatarUrl,
    reposUrl = reposUrl,
    followersUrl = followersUrl,
    subscriptionsUrl = subscriptionsUrl
)

fun List<NetworkUser>.networkToUserModels() = map { it.toUserModel() }