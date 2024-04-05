package com.sample.multiplatform.db

import com.sample.multiplatform.models.UserModel
import data.Users

fun mapTo(user: Users): UserModel {
    return UserModel(
        id = user.idInner,
        login = user.login,
        url = user.url,
        avatarUrl = user.avatarUrl,
        reposUrl = user.reposUrls,
        followersUrl = user.followersUrl,
        subscriptionsUrl = user.subscriptionsUrl
    )
}