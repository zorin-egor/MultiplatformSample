package com.sample.app.feature.users.models

sealed interface UsersEvent {
    data object None : UsersEvent
    data object OnBottomEnd : UsersEvent
    class OnUserClick(val user: String) : UsersEvent
}