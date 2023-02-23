package com.sample.multiplatform.models

sealed interface UsersAction {
    class ShowError(val message: String) : UsersAction
}