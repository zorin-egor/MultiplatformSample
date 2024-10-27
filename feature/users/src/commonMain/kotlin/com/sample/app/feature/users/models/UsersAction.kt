package com.sample.app.feature.users.models

sealed interface UsersAction {
    class ShowError(val message: String) : UsersAction
    class OpenDetails(val user: String) : UsersAction
}