package com.sample.app.feature.details.models

import com.sample.app.core.model.DetailsModel

data class DetailsViewState(
    val details: DetailsModel? = null,
    val isCenterProgress: Boolean = false
)