package com.sample.multiplatform

import com.sample.multiplatform.models.User

interface UsersRepository {

    companion object {
        const val DEFAULT_SINCE_USER = 0L
    }

    suspend fun getUsers(since: Long): List<User>

}