package com.sample.app.core.data.repositories.user_details

import com.sample.app.core.common.result.Result
import com.sample.app.core.model.UserDetailsModel
import kotlinx.coroutines.flow.Flow

interface UserDetailsRepository {

    suspend fun getUserDetails(id: Long, url: String): Flow<Result<UserDetailsModel>>

}