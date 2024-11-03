package com.sample.app.core.domain

import com.sample.app.core.common.result.Result
import com.sample.app.core.data.repositories.repositories.RepositoriesRepository
import com.sample.app.core.model.RepositoryModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock


class GetRepositoriesByNameUseCase(
    private val repositoriesRepository: RepositoriesRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default,
) {

    companion object {
        private const val LIMIT = 30L
        private const val START_PAGE = 1L
    }

    private val repositories = ArrayList<RepositoryModel>()
    private val mutex = Mutex()
    private var previousName = ""
    private var hasNext = true
    private var page = START_PAGE
    private var limit = LIMIT

    suspend operator fun invoke(name: String = previousName): Flow<Result<List<RepositoryModel>>> {
        println("invoke($name, $previousName)")

        val p = mutex.withLock {
            when {
                name.isEmpty() -> return flowOf(Result.Success(data = emptyList()))
                name == previousName && !hasNext -> return flowOf(Result.Success(repositories.toList()))
                name != previousName -> page = START_PAGE
            }
            page
        }

        return repositoriesRepository.getRepositoriesByName(name = name, page = p, limit = limit)
            .map { new ->
                if (new is Result.Success) {
                    mutex.withLock {
                        if (name != previousName) repositories.clear()
                        previousName = name
                        ++page
                        hasNext = new.data.size >= limit
                        new.data.forEach { item ->
                            if (repositories.find { it.id == item.id } == null) {
                                repositories.add(item)
                            }
                        }

                        println("map() - $repositories")
                        Result.Success(repositories.toList())
                    }
                } else {
                    new
                }
            }
            .flowOn(dispatcher)
    }

}