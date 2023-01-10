package com.sample.multiplatform

import com.sample.multiplatform.models.User

interface UsersRepository {

    suspend fun getUsers(since: Long): List<User>

}