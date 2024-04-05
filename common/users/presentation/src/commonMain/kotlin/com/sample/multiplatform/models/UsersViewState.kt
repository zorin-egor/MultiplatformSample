package com.sample.multiplatform.models

data class UsersViewState(
    val users: List<UserModel> = emptyList(),
    val isCenterProgress: Boolean = false,
    val isBottomProgress: Boolean = false,
)