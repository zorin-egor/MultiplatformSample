package com.sample.app.core.domain

import com.sample.app.core.data.repositories.repository_details.RepositoryDetailsRepository
import com.sample.app.core.model.RepositoryDetailsModel
import com.sample.architecturecomponents.core.di.Dispatcher
import com.sample.architecturecomponents.core.di.Dispatchers
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


class GetRepositoryDetailsByOwnerUseCase @Inject constructor(
    private val repositoryDetailsRepository: RepositoryDetailsRepository,
    @Dispatcher(Dispatchers.IO) val dispatcher: CoroutineDispatcher
) {

    operator fun invoke(owner: String, repo: String): Flow<Result<RepositoryDetailsModel>> =
        repositoryDetailsRepository.getDetails(owner = owner, repo = repo)
            .flowOn(dispatcher)
}
