package com.sample.app.core.domain

import com.sample.app.core.common.result.Result
import com.sample.app.core.data.repositories.users.UsersRepository
import com.sample.app.core.model.UserModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class GetUsersUseCase(
    private val usersRepository: UsersRepository,
    private val dispatcher: CoroutineDispatcher,
) {

    companion object {
        const val SINCE_ID = 0L
        private const val LIMIT = 30L
        private const val UNDEFINED = -1L
    }

    private val mutex = Mutex()
    private var sinceId = UNDEFINED
    private var lastId = SINCE_ID
    private var limit = LIMIT

    suspend operator fun invoke(id: Long = lastId): Flow<Result<List<UserModel>>> {
        println("GetUsersUseCase($id)")

        val (sinceUserId, lastUserId, limitUsers) = mutex.withLock {
            if (sinceId == UNDEFINED) {
                sinceId = id
                lastId = id
            }
            Triple(sinceId, lastId, limit)
        }

        return usersRepository.getUsers(sinceId = sinceUserId, lastId = lastUserId, limit = limitUsers)
            .onEach { new ->
                if (new is Result.Success) {
                    mutex.withLock {
                        limit += LIMIT
                        lastId = new.data.lastOrNull()?.id ?: lastId
                    }
                }
            }.flowOn(dispatcher)
    }

}
