package com.sample.app.core.data.database

import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

internal const val MODULE_DATABASE = "databaseModule"

internal val databaseModule = DI.Module(MODULE_DATABASE) {
    bind<DbDriverFactory>() with singleton {
        DbDriverFactory(instance())
    }

    bind<AppDatabase>() with singleton {
        AppDatabaseImpl(instance<DbDriverFactory>())
    }
}