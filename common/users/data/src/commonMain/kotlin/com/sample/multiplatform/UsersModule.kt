package com.sample.multiplatform

import com.sample.multiplatform.db.dbUsersModule
import com.sample.multiplatform.ktor.KtorUsersDataSource
import com.sample.multiplatform.settings.SettingsUsersSource
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.provider
import org.kodein.di.singleton

val usersModule = DI.Module("com.sample.multiplatform.getUsersDataModule") {
    import(dbUsersModule)

    bind<UsersRepository>() with singleton {
        UsersRepositoryImpl(instance(tag = UsersRepository::class.simpleName), instance(), instance())
    }

    bind<SettingsUsersSource>() with provider {
        SettingsUsersSource(instance())
    }

    bind<KtorUsersDataSource>() with provider {
        KtorUsersDataSource(instance())
    }
}