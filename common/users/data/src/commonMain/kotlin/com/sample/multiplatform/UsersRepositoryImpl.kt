package com.sample.multiplatform

import com.sample.multiplatform.ktor.KtorUsersDataSource
import com.sample.multiplatform.ktor.KtorUsersRequest
import com.sample.multiplatform.ktor.mapTo
import com.sample.multiplatform.models.User
import com.sample.multiplatform.settings.SettingsUsersSource
import com.sample.multiplatform.users.data.Database

internal class UsersRepositoryImpl(
    private val database: Database,
    private val remoteDataSource: KtorUsersDataSource,
    private val cacheDataSource: SettingsUsersSource
) : UsersRepository {

    override suspend fun getUsers(since: Long): List<User> {
        val dbUsers = database.usersQueries.selectAll().executeAsList()
        database.settingsQueries.insert("key1", "value1")
        val dbSettings = database.settingsQueries.selectAll().executeAsList()
        println("UsersRepositoryImpl() - db users and settings: ${dbUsers.size}, ${dbSettings.size}")
        val users = remoteDataSource.getUsers(KtorUsersRequest(since)).map(::mapTo)
        cacheDataSource.saveSince(since + users.size)
        return users
    }

}