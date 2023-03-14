package com.sample.multiplatform.image_loader

import com.sample.multiplatform.PlatformConfiguration
import com.seiko.imageloader.ImageLoader
import com.seiko.imageloader.cache.memory.maxSizePercent
import com.seiko.imageloader.component.setupDefaultComponents
import kotlinx.coroutines.Dispatchers
import okio.Path.Companion.toOkioPath

actual class ImageLoaderConfig actual constructor(private val platformConfig: PlatformConfiguration) {
    actual fun getDefaultImageLoader(): ImageLoader {
        return ImageLoader(requestCoroutineContext = Dispatchers.IO) {
            commonConfig()

//            options.apply {
//                memoryCachePolicy = CachePolicy.DISABLED
//                diskCachePolicy = CachePolicy.DISABLED
//            }

            components {
                setupDefaultComponents(platformConfig.context)
            }

            interceptor {
                memoryCacheConfig {
                    maxSizePercent(platformConfig.context, 0.25)
                }
                diskCacheConfig {
                    directory(platformConfig.context.cacheDir.resolve("image_cache").toOkioPath())
                    maxSizeBytes(512L * 1024 * 1024)
                }
            }
        }
    }

}