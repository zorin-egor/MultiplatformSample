package com.sample.multiplatform

import com.sample.multiplatform.di.Inject
import com.sample.multiplatform.image_loader.ImageLoaderConfig
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.direct
import org.kodein.di.singleton

object PlatformProviderSDK {

    fun init(
        configuration: PlatformConfiguration
    ) {
        val providerModule = DI.Module(
            name = "platform_provider",
            init = {
                bind<PlatformConfiguration>() with singleton { configuration }
                bind<ImageLoaderConfig>() with singleton { ImageLoaderConfig(configuration) }
            }
        )

        Inject.createDependencies(
            DI {
                importAll(
                    providerModule,
                    coreModule,
                    usersModule,
                    detailsModule
                )
            }.direct
        )
    }
}