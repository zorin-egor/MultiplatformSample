package com.sample.app.feature.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.app.common.di.Inject
import com.sample.app.common.result.Result
import com.sample.app.core.domain.GetUserDetailsUseCase
import com.sample.app.core.model.UserModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class DetailsViewModel : ViewModel() {

    val getDetailsUseCase: GetUserDetailsUseCase = Inject.instance()

    private val _users = MutableStateFlow<List<UserModel>>(emptyList())
    val users = _users.asStateFlow()

    fun getUsers(id: Long, url: String) {
        viewModelScope.launch {
            getDetailsUseCase(id, url).collect {
                when(it) {
                    is Result.Error -> {}
                    Result.Loading -> {}
                    is Result.Success -> {
                        _users.tryEmit(listOf(it.data))
                    }
                }
            }
        }
    }

}