package com.sample.app.core.data.repositories.users

import com.sample.app.common.result.Result
import com.sample.app.core.model.UserModel
import kotlinx.coroutines.flow.Flow

interface UsersRepository {

    fun getUsers(sinceId: Long, lastId: Long, limit: Long = 30): Flow<Result<List<UserModel>>>

}