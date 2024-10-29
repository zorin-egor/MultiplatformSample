package com.sample.app.feature.details.models

sealed interface UserDetailsEvent {
    data object None : UserDetailsEvent
    data class GetUser(val id: Long, val url: String) : UserDetailsEvent
    data object ShareProfile : UserDetailsEvent
    data object NavigationBack : UserDetailsEvent
}