package com.sample.app.core.ui.ext

import com.sample.app.core.model.exceptions.CommonException
import com.sample.app.core.model.exceptions.EmptyException
import com.sample.app.core.model.exceptions.NetworkException
import com.sample.app.core.ui.resources.Res
import com.sample.app.core.ui.resources.core_common_error_network_400
import com.sample.app.core.ui.resources.core_common_error_network_401
import com.sample.app.core.ui.resources.core_common_error_network_403
import com.sample.app.core.ui.resources.core_common_error_network_404
import com.sample.app.core.ui.resources.core_common_error_network_405
import com.sample.app.core.ui.resources.core_common_error_network_408
import com.sample.app.core.ui.resources.core_common_error_network_409
import com.sample.app.core.ui.resources.core_common_error_network_415
import com.sample.app.core.ui.resources.core_common_error_network_500
import com.sample.app.core.ui.resources.core_common_error_network_unknown
import com.sample.app.core.ui.resources.core_common_error_no_data
import com.sample.app.core.ui.resources.core_common_error_unknown
import com.sample.app.core.ui.resources.core_common_error_with_code
import org.jetbrains.compose.resources.StringResource


internal val NETWORK_ERRORS = mapOf(
    400 to Res.string.core_common_error_network_400,
    401 to Res.string.core_common_error_network_401,
    403 to Res.string.core_common_error_network_403,
    404 to Res.string.core_common_error_network_404,
    405 to Res.string.core_common_error_network_405,
    408 to Res.string.core_common_error_network_408,
    409 to Res.string.core_common_error_network_409,
    415 to Res.string.core_common_error_network_415,
    500 to Res.string.core_common_error_network_500,
)

val Throwable.toStringResource: StringResource
    get() = when(val error = this) {
        is EmptyException -> Res.string.core_common_error_no_data
        is CommonException -> error.code?.let { Res.string.core_common_error_with_code }
                ?: Res.string.core_common_error_unknown
        is NetworkException -> NETWORK_ERRORS[error.errorCode]
            ?: Res.string.core_common_error_network_unknown
        else -> Res.string.core_common_error_unknown
    }