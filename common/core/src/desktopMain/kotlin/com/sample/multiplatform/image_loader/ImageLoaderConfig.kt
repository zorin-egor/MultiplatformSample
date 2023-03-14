package com.sample.multiplatform.image_loader

import com.sample.multiplatform.PlatformConfiguration
import com.seiko.imageloader.ImageLoader

actual class ImageLoaderConfig actual constructor(private val platformConfig: PlatformConfiguration) {
    actual fun getDefaultImageLoader(): ImageLoader {
        TODO("Not yet implemented")
    }

}