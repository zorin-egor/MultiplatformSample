package com.sample.multiplatform

import com.sample.multiplatform.models.UserModel

interface UsersRepository {

    companion object {
        const val DEFAULT_SINCE_USER = 0L
    }

    suspend fun getUsers(since: Long, isUseOnlyCache: Boolean = false): List<UserModel>

}