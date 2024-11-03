package com.sample.app.core.model.exceptions

open class CommonException(
    val error: String? = null,
    val code: Int? = null
) : Exception(error)