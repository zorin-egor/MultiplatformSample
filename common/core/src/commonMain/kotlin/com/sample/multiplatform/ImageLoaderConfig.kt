package com.sample.multiplatform

import com.seiko.imageloader.ImageLoader
import com.seiko.imageloader.ImageLoaderConfigBuilder
import com.seiko.imageloader.util.DebugLogger
import com.seiko.imageloader.util.LogPriority

expect class ImageLoaderConfig(platformConfig: PlatformConfiguration) {

    fun getDefaultImageLoader(): ImageLoader
}

fun ImageLoaderConfigBuilder.commonConfig() {
    logger = DebugLogger(LogPriority.VERBOSE)
    interceptor {
//        addInterceptor(BlurInterceptor())
    }
}