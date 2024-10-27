package com.sample.app.core.data.repositories.details

import app.cash.sqldelight.async.coroutines.awaitAsList
import com.sample.app.common.result.Result
import com.sample.app.core.data.database.AppDatabase
import com.sample.app.core.data.model.mapTo
import com.sample.app.core.datastore.settings.SettingsSource
import com.sample.app.core.model.UserModel
import com.sample.app.core.network.requests.details.KtorDetailsDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart

class DetailsRepositoryImpl(
    private val network: KtorDetailsDataSource,
    private val database: AppDatabase,
    private val settings: SettingsSource,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) : DetailsRepository {

    override fun getUserDetails(id: Long, url: String): Flow<Result<UserModel>> =
        flow<Result<UserModel>> {
            val user = database.db().usersQueries.selectUser(id).awaitAsList().first()
            println("DetailsRepositoryImpl() - $user")
            emit(Result.Success(mapTo(user)))
        }
        .onStart { emit(Result.Loading) }
        .catch {
            println("DetailsRepositoryImpl() - $it")
            emit(Result.Error(it))
        }

}