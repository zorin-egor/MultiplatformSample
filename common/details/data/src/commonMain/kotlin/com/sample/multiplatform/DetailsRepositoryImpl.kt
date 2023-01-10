package com.sample.multiplatform

import com.sample.multiplatform.ktor.KtorDetailsDataSource
import com.sample.multiplatform.ktor.KtorDetailsRequest
import com.sample.multiplatform.ktor.mapTo
import com.sample.multiplatform.models.Details
import com.sample.multiplatform.settings.SettingsDetailsSource

class DetailsRepositoryImpl(
    private val remoteDataSource: KtorDetailsDataSource,
    private val cacheDataSource: SettingsDetailsSource
) : DetailsRepository {

    override suspend fun getUserDetails(url: String): Details {
        val details = remoteDataSource.getDetails(KtorDetailsRequest(url)).let(::mapTo)
        return details
    }

}