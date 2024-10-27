package com.sample.app.core.data.model

import com.sample.app.core.model.UserModel
import data.UsersEntity


fun mapTo(user: UsersEntity): UserModel {
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