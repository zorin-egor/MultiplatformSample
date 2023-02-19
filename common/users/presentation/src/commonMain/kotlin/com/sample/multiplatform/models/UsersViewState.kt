package com.sample.multiplatform.models

data class UsersViewState(
    val users: List<User> = emptyList(),
    val isCenterProgress: Boolean = false,
    val isBottomProgress: Boolean = false,
)