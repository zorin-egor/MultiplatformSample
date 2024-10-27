package com.sample.app.feature.users.models

data class UsersViewState(
    val users: List<String> = emptyList(),
    val isCenterProgress: Boolean = false,
    val isBottomProgress: Boolean = false,
)