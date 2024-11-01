package com.sample.app.core.ui.ext

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.size.Scale
import coil3.size.Size

@Composable
fun ImageRequest(
    url: String?,
    isError: MutableState<Boolean>,
    isLoading: MutableState<Boolean>
): ImageRequest.Builder =
    ImageRequest.Builder(LocalPlatformContext.current)
        .data(url)
        .scale(Scale.FILL)
        .size(Size.ORIGINAL)
        .crossfade(true)
        .listener(
            onStart = { isLoading.value = true },
            onError = { _, _ -> isError.value = true },
            onSuccess = { _, _ ->  isLoading.value = false }
        )

