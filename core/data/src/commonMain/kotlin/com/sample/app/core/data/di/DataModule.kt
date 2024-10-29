package com.sample.app.core.data.di

import com.sample.app.core.data.database.databaseModule
import com.sample.app.core.data.repositories.user_details.UserDetailsRepository
import com.sample.app.core.data.repositories.user_details.UserDetailsRepositoryImpl
import com.sample.app.core.data.repositories.users.UsersRepository
import com.sample.app.core.data.repositories.users.UsersRepositoryImpl
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

const val MODULE_DATA = "dataModule"

val dataModule = DI.Module(MODULE_DATA) {
    import(databaseModule)
    bind<UserDetailsRepository>() with singleton { UserDetailsRepositoryImpl(instance(), instance(), instance()) }
    bind<UsersRepository>() with singleton { UsersRepositoryImpl(instance(), instance(), instance()) }
}