package com.sample.app.core.domain.di

import com.sample.app.core.domain.GetRecentSearchUseCase
import com.sample.app.core.domain.GetRepositoriesByNameUseCase
import com.sample.app.core.domain.GetRepositoryDetailsByOwnerUseCase
import com.sample.app.core.domain.GetUserDetailsUseCase
import com.sample.app.core.domain.GetUsersUseCase
import com.sample.app.core.domain.SetRecentSearchUseCase
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.provider
import org.kodein.di.singleton

const val MODULE_DOMAIN = "domainModule"

val domainModule = DI.Module(MODULE_DOMAIN) {
    bind<GetUsersUseCase>() with provider { GetUsersUseCase(instance()) }
    bind<GetUserDetailsUseCase>() with singleton { GetUserDetailsUseCase(instance()) }
    bind<GetRepositoriesByNameUseCase>() with singleton { GetRepositoriesByNameUseCase(instance()) }
    bind<GetRepositoryDetailsByOwnerUseCase>() with singleton { GetRepositoryDetailsByOwnerUseCase(instance()) }
    bind<GetRecentSearchUseCase>() with singleton { GetRecentSearchUseCase(instance()) }
    bind<SetRecentSearchUseCase>() with singleton { SetRecentSearchUseCase(instance()) }
}