package com.sample.multiplatform

import com.sample.multiplatform.details.data.Database
import com.sample.multiplatform.ktor.KtorDetailsDataSource
import com.sample.multiplatform.ktor.KtorDetailsRequest
import com.sample.multiplatform.ktor.mapTo
import com.sample.multiplatform.models.Details
import com.sample.multiplatform.settings.SettingsDetailsSource

class DetailsRepositoryImpl(
    private val database: Database,
    private val remoteDataSource: KtorDetailsDataSource,
    private val cacheDataSource: SettingsDetailsSource
) : DetailsRepository {

    override suspend fun getUserDetails(url: String): Details {
        try {
            database.detailsQueries.insert(0, "login1", "avatarUrl", "url", "reposUrls")
        } catch (e: Exception) {
            println("AAAAA: $e")
        }
        val dbDetails = database.detailsQueries.selectAll().executeAsList()
        println("AAAAA: ${dbDetails.size}")
        val details = remoteDataSource.getDetails(KtorDetailsRequest(url)).let(::mapTo)
        return details
    }

}