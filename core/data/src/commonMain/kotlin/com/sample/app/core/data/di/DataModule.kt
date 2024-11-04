package com.sample.app.core.data.di

import com.sample.app.core.common.di.DI_TAG_SCOPE_IO
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

const val DI_MODULE_DATA = "dataModule"

val dataModule = DI.Module(DI_MODULE_DATA) {
    import(databaseModule)
    bind<UserDetailsRepository>() with singleton {
        UserDetailsRepositoryImpl(instance(), instance(), instance(), instance(tag = DI_TAG_SCOPE_IO))
    }
    bind<UsersRepository>() with singleton {
        UsersRepositoryImpl(instance(), instance(), instance(), instance(tag = DI_TAG_SCOPE_IO))
    }
    bind<RepositoriesRepository>() with singleton {
        RepositoriesRepositoryImpl(instance(), instance(), instance(tag = DI_TAG_SCOPE_IO))
    }
    bind<RepositoryDetailsRepository>() with singleton {
        RepositoryDetailsRepositoryImpl(instance(), instance(), instance(tag = DI_TAG_SCOPE_IO))
    }
    bind<RecentSearchRepository>() with singleton {
        RecentSearchRepositoryImpl(instance(), instance(tag = DI_TAG_SCOPE_IO))
    }
}