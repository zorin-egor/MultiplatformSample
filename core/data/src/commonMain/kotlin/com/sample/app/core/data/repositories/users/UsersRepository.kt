package com.sample.app.core.data.repositories.users

import com.sample.app.common.result.Result
import com.sample.app.core.model.UserModel
import kotlinx.coroutines.flow.Flow

interface UsersRepository {

    suspend fun getUsers(sinceId: Long, limit: Long): Flow<Result<List<UserModel>>>

}