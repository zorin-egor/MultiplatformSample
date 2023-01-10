package com.sample.multiplatform

import com.sample.multiplatform.models.Details

interface DetailsRepository {

    suspend fun getUserDetails(url: String): Details

}