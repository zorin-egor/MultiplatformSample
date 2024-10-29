package com.sample.app.core.domain

import com.sample.app.common.result.Result
import com.sample.app.core.data.repositories.details.DetailsRepository
import com.sample.app.core.model.DetailsModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class GetUserDetailsUseCase(
    private val userDetailsRepository: DetailsRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) {

    operator fun invoke(userId: Long, url: String): Flow<Result<DetailsModel>> =
        userDetailsRepository.getUserDetails(id = userId, url = url)
            .flowOn(dispatcher)
}
