package com.sample.app.core.data.model

import com.sample.app.core.model.UserDetailsModel
import com.sample.app.core.network.converters.dateTimeConverter
import com.sample.app.core.network.models.NetworkUserDetails
import data.UserDetailsEntity

fun NetworkUserDetails.toUserDetailsModel() = UserDetailsModel(
    id = id,
    url = htmlUrl,
    avatarUrl = avatarUrl,
    name = name,
    company = company,
    blog = blog,
    location = location,
    email = email,
    bio = bio,
    publicRepos = publicRepos,
    publicGists = publicGists,
    followers = followers,
    following = following,
    createdAt = dateTimeConverter(createdAt),
    updatedAt = dateTimeConverter(updatedAt),
    reposUrl = reposUrl,
    hireable = hireable ?: false
)

fun UserDetailsEntity.toUserDetailsModel() = UserDetailsModel(
    id = id,
    name = name,
    url = url,
    avatarUrl = avatarUrl,
    reposUrl = reposUrls,
    company = company,
    blog = blog,
    location = location,
    email = email,
    hireable = hireable.toBoolean(),
    bio = bio,
    publicRepos = publicRepos,
    publicGists = publicGists,
    followers = followers,
    following = following,
    createdAt = createdAt?.let(::dateTimeConverter),
    updatedAt = updatedAt?.let(::dateTimeConverter),
)