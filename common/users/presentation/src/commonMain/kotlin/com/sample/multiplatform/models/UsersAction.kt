package com.sample.multiplatform.models

interface Closes

sealed interface UsersAction {
    class ShowError(val message: String) : UsersAction

    class OpenDetails(val user: UserModel) : UsersAction, Closes
}