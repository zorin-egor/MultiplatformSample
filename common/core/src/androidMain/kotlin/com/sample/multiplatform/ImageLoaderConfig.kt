package com.sample.multiplatform

import com.seiko.imageloader.ImageLoader
import com.seiko.imageloader.cache.memory.maxSizePercent
import com.seiko.imageloader.component.setupDefaultComponents
import okio.Path.Companion.toOkioPath

actual class ImageLoaderConfig actual constructor(private val platformConfig: PlatformConfiguration) {
    actual fun getDefaultImageLoader(): ImageLoader {
        return ImageLoader {
            commonConfig()
            components {
                setupDefaultComponents(platformConfig.context)
            }
            interceptor {
                memoryCacheConfig {
                    // Set the max size to 25% of the app's available memory.
                    maxSizePercent(platformConfig.context, 0.25)
                }
                diskCacheConfig {
                    directory(platformConfig.context.cacheDir.resolve("image_cache").toOkioPath())
                    maxSizeBytes(512L * 1024 * 1024) // 512MB
                }
            }
        }
    }

}