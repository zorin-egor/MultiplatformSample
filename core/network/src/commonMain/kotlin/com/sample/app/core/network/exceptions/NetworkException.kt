package com.sample.app.core.network.exceptions

import kotlinx.io.IOException

open class NetworkException(
    val errorCode: Int = Int.MIN_VALUE,
    private val errorDesc: String? = null
) : IOException(errorDesc)
