package com.sample.app.core.data.repositories.repositories

import com.sample.app.core.model.RepositoryModel
import kotlinx.coroutines.flow.Flow

interface RepositoriesRepository {

    fun getRepositoriesByName(name: String, page: Long, limit: Long = 30, isDescSort: Boolean = false): Flow<Result<List<RepositoryModel>>>

    suspend fun insert(item: RepositoryModel)

    suspend fun delete(item: RepositoryModel)

}