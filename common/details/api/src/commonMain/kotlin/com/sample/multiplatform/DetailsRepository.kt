package com.sample.multiplatform

import com.sample.multiplatform.models.DetailsModel

interface DetailsRepository {

    suspend fun getUserDetails(id: Long, url: String): DetailsModel

}