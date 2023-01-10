package com.sample.multiplatform

import com.sample.multiplatform.ktor.KtorDetailsDataSource
import com.sample.multiplatform.settings.SettingsDetailsSource
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.provider
import org.kodein.di.singleton

val detailsModule = DI.Module("com.sample.multiplatform.getDetailsModule") {
    bind<DetailsRepository>() with singleton {
        DetailsRepositoryImpl(instance(), instance())
    }

    bind<SettingsDetailsSource>() with provider {
        SettingsDetailsSource(instance())
    }

    bind<KtorDetailsDataSource>() with provider {
        KtorDetailsDataSource(instance())
    }
}