package com.sample.app.core.data.repositories.repository_details

import com.sample.app.core.common.result.Result
import com.sample.app.core.model.RepositoryDetailsModel
import kotlinx.coroutines.flow.Flow

interface RepositoryDetailsRepository {

    suspend fun getDetails(owner: String, repo: String): Flow<Result<RepositoryDetailsModel>>

    suspend fun insert(item: RepositoryDetailsModel)

    suspend fun delete(item: RepositoryDetailsModel)

}