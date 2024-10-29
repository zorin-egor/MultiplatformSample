package com.sample.app.core.data.model

import com.sample.app.core.model.DetailsModel
import data.DetailsEntity

fun mapTo(user: DetailsEntity): DetailsModel {
    return DetailsModel(
        id = user.id,
        nodeId = user.nodeId,
        login = user.login,
        name = "",
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
        updatedAt = user.updatedAt,
    )
}