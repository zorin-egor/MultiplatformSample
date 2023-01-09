package com.sample.multiplatform

import com.sample.multiplatform.ktor.KtorUsersDataSource
import com.sample.multiplatform.ktor.KtorUsersRequest
import com.sample.multiplatform.models.User
import com.sample.multiplatform.settings.SettingsUsersSource

class UsersRepositoryImpl(
    private val remoteDataSource: KtorUsersDataSource,
    private val cacheDataSource: SettingsUsersSource
) : UsersRepository {

    override suspend fun getUsers(since: Long): List<User> {
        val users = remoteDataSource.getUsers(KtorUsersRequest(since))
        cacheDataSource.saveSince(since + users.size)
        return users
    }

}