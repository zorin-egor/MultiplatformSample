package com.sample.app.core.domain

import com.sample.app.core.common.result.Result
import com.sample.app.core.data.repositories.repository_details.RepositoryDetailsRepository
import com.sample.app.core.model.RepositoryDetailsModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class GetRepositoryDetailsByOwnerUseCase(
    private val repositoryDetailsRepository: RepositoryDetailsRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) {

    suspend operator fun invoke(owner: String, repo: String): Flow<Result<RepositoryDetailsModel>> =
        repositoryDetailsRepository.getDetails(owner = owner, repo = repo)
            .flowOn(dispatcher)
}
