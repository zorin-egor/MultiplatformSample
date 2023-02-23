package com.sample.multiplatform

import com.sample.multiplatform.di.Inject
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
            }
        )

        Inject.createDependencies(
            DI {
                importAll(
                    providerModule,
                    coreModule,
                    dataUsersModule,
                    detailsModule
                )
            }.direct
        )
    }
}