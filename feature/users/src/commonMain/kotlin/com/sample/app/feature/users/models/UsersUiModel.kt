package com.sample.app.feature.users.models

import com.sample.app.core.model.UserModel

data class UsersUiModel(
    val users: List<UserModel>,
    val isBottomProgress: Boolean
)
