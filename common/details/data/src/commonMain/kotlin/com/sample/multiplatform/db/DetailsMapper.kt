package com.sample.multiplatform.db

import com.sample.multiplatform.models.DetailsModel
import data.Details

fun mapTo(user: Details): DetailsModel {
    return DetailsModel(
        id = user.idInner,
        nodeId = user.nodeId,
        login = user.login,
        url = user.url,
        avatarUrl = user.avatarUrl,
        reposUrl = user.reposUrls,
        followersUrl = user.followersUrl,
        subscriptionsUrl = user.subscriptionsUrl,
        followingUrl = user.followingUrl,
        gistsUrl = user.gistsUrl,
        starredUrl = user.starredUrl,
        organizationsUrl = user.organizationsUrl,
        eventsUrl = user.eventsUrl,
        receivedEventsUrl = user.receivedEventsUrl,
        company = user.company,
        blog = user.blog,
        location = user.location,
        email = user.email,
        hireable = user.hireable,
        bio = user.bio,
        publicRepos = user.publicRepos,
        publicGists = user.publicGists,
        followers = user.followers,
        following = user.following,
        createdAt = user.createdAt,
        updatedAt = user.updatedAt
    )
}