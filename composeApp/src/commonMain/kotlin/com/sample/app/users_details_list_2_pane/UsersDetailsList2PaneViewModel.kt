package com.sample.app.users_details_list_2_pane

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class UsersDetailsList2PaneViewModel() : ViewModel() {

    val selectedUser: StateFlow<Pair<Long, String>?> = MutableStateFlow(null)
//        savedStateHandle.getStateFlow<Long?>(USER_ID_ARG, null)
//            .filterNotNull()
//            .zip(savedStateHandle.getStateFlow<String?>(USER_URL_ARG, null)
//            .filterNotNull()) { id, url ->
//                Pair(id, url)
//            }.stateIn(
//                scope = viewModelScope,
//                started = SharingStarted.Lazily,
//                initialValue = null
//            )

    fun onUserClick(userId: Long, userUrl: String) {
        println("UsersDetailsList2PaneViewModel() - onUserClick($userId, $userUrl)")
//        savedStateHandle[USER_ID_ARG] = userId
//        savedStateHandle[USER_URL_ARG] = userUrl
    }
}

