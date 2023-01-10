package com.sample.multiplatform.ktor

import kotlinx.serialization.Serializable

@Serializable
data class KtorDetailsRequest(
    val url: String
)
