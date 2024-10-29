package com.sample.app.feature.users.models

import com.sample.app.core.model.UserModel


sealed interface UsersEvents {
    data object None : UsersEvents
    data class OnUserClick(val item: UserModel): UsersEvents
    data object NextUser : UsersEvents
}