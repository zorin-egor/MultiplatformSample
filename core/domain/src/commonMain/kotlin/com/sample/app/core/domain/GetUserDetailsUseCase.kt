package com.sample.app.core.domain

import com.sample.app.core.common.result.Result
import com.sample.app.core.data.repositories.user_details.UserDetailsRepository
import com.sample.app.core.model.UserDetailsModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class GetUserDetailsUseCase(
    private val userDetailsRepository: UserDetailsRepository,
    private val dispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke(userId: Long, url: String): Flow<Result<UserDetailsModel>> =
        userDetailsRepository.getUserDetails(id = userId, url = url)
            .flowOn(dispatcher)
}
