package com.sample.multiplatform.models

sealed interface UsersEvent {
    object OnBottomEnd : UsersEvent
    class OnUserClick(val user: UserModel) : UsersEvent

    object ResetAction : UsersEvent
}