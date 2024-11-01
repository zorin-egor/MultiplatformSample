package com.sample.app

import com.sample.app.core.common.di.Inject
import com.sample.app.core.data.di.dataModule
import com.sample.app.core.datastore.di.dataStoreModule
import com.sample.app.core.domain.di.domainModule
import com.sample.app.core.model.AppConfigModel
import com.sample.app.core.network.di.networkModule
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.direct
import org.kodein.di.singleton

object PlatformProvider {

    fun init(configuration: AppConfigModel) {
        val providerModule = DI.Module(
            name = "platform_provider",
            init = {
                bind<AppConfigModel>() with singleton { configuration }
            }
        )

        Inject.createDependencies(
            DI {
                importAll(
                    providerModule,
                    networkModule,
                    dataStoreModule,
                    dataModule,
                    domainModule,
                )
            }.direct
        )
    }
}