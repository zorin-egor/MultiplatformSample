package com.sample.app.core.data.repositories.details

import com.sample.app.common.result.Result
import com.sample.app.core.model.DetailsModel
import kotlinx.coroutines.flow.Flow

interface DetailsRepository {

    fun getUserDetails(id: Long, url: String): Flow<Result<DetailsModel>>

}