package com.sample.multiplatform.models

sealed interface UsersAction {
    class OpenDetails(val user: User) : UsersAction
    class ShowError(val message: String) : UsersAction
}