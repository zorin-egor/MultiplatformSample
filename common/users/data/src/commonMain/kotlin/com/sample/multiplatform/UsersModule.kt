package com.sample.multiplatform

import com.sample.multiplatform.ktor.KtorUsersDataSource
import com.sample.multiplatform.settings.SettingsUsersSource
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.provider
import org.kodein.di.singleton

val usersModule = DI.Module("com.sample.multiplatform.getUsersModule") {
    bind<UsersRepository>() with singleton {
        UsersRepositoryImpl(instance(), instance())
    }

    bind<SettingsUsersSource>() with provider {
        SettingsUsersSource(instance())
    }

    bind<KtorUsersDataSource>() with provider {
        KtorUsersDataSource(instance())
    }
}