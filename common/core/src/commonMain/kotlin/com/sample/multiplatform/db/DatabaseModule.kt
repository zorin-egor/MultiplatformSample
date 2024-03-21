package com.sample.multiplatform.db

import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

val dbFactoryModule = DI.Module("dbFactoryModule") {
    bind<DriverFactory>() with singleton {
        DriverFactory(instance())
    }

    bind<GitHubDriverFactory>() with singleton {
        GitHubDriverFactory(instance())
    }
}