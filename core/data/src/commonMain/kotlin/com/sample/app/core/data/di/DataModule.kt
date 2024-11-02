package com.sample.app.core.data.di

import com.sample.app.core.data.database.databaseModule
import com.sample.app.core.data.repositories.recent_search.RecentSearchRepository
import com.sample.app.core.data.repositories.recent_search.RecentSearchRepositoryImpl
import com.sample.app.core.data.repositories.repositories.RepositoriesRepository
import com.sample.app.core.data.repositories.repositories.RepositoriesRepositoryImpl
import com.sample.app.core.data.repositories.repository_details.RepositoryDetailsRepository
import com.sample.app.core.data.repositories.repository_details.RepositoryDetailsRepositoryImpl
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
    bind<RepositoriesRepository>() with singleton { RepositoriesRepositoryImpl(instance(), instance()) }
    bind<RepositoryDetailsRepository>() with singleton { RepositoryDetailsRepositoryImpl(instance(), instance()) }
    bind<RecentSearchRepository>() with singleton { RecentSearchRepositoryImpl(instance()) }
}