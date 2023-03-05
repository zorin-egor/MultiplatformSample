package com.sample.multiplatform.mappers

import com.sample.multiplatform.models.Details
import com.sample.multiplatform.models.User

fun User.mapTo(): Details {
    return Details(
        id = id,
        login = login,
        url = url,
        avatarUrl = avatarUrl,
    )
}