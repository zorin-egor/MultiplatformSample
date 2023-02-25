package com.sample.multiplatform

import com.seiko.imageloader.ImageLoader

actual class ImageLoaderConfig actual constructor(private val platformConfig: PlatformConfiguration) {
    actual fun getDefaultImageLoader(): ImageLoader {
        TODO("Not yet implemented")
    }

}