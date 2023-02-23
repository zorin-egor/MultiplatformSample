package com.sample.multiplatform.models

sealed interface UsersNavigation {
    class OpenDetails(val user: User) : UsersNavigation

}