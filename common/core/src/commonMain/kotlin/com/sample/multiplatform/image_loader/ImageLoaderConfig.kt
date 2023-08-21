package com.sample.multiplatform.image_loader

import com.sample.multiplatform.PlatformConfiguration
import com.seiko.imageloader.ImageLoader
import com.seiko.imageloader.ImageLoaderConfigBuilder
import com.seiko.imageloader.util.Logger

expect class ImageLoaderConfig(platformConfig: PlatformConfiguration) {

    fun getDefaultImageLoader(): ImageLoader
}

fun ImageLoaderConfigBuilder.commonConfig() {
    logger = Logger.None
    interceptor {
        addInterceptor(NullDataInterceptor)
    }
}