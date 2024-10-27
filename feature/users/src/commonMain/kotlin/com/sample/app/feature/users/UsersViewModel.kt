package com.sample.app.feature.users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.app.common.di.Inject
import com.sample.app.common.result.Result
import com.sample.app.core.domain.GetUsersUseCase
import com.sample.app.core.model.UserModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class UsersViewModel : ViewModel() {

    val getUseCase: GetUsersUseCase = Inject.instance()

    private val _users = MutableStateFlow<List<UserModel>>(emptyList())
    val users = _users.asStateFlow()

    private val _click = MutableStateFlow(false)
    val click = _click.asStateFlow()

    init {
        getUsers()
    }

    private fun getUsers() {
        viewModelScope.launch {
            getUseCase()
                .collect {
                    when(it) {
                        is Result.Error -> {}
                        Result.Loading -> {}
                        is Result.Success -> {
                            _users.tryEmit(it.data)
                        }
                    }
                }
        }
    }

    fun onClick() {
        _click.tryEmit(true)
    }

    fun reset() {
        _click.update { false }
    }

}