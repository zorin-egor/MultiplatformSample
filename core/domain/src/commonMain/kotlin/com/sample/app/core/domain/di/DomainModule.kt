package com.sample.app.core.domain.di

import com.sample.app.core.common.di.DI_TAG_DISPATCHER_IO
import com.sample.app.core.common.di.DI_TAG_SCOPE_IO
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

const val DI_MODULE_DOMAIN = "domainModule"

val domainModule = DI.Module(DI_MODULE_DOMAIN) {
    bind<GetUsersUseCase>() with provider { GetUsersUseCase(instance(), instance(tag = DI_TAG_DISPATCHER_IO)) }
    bind<GetUserDetailsUseCase>() with singleton { GetUserDetailsUseCase(instance(), instance(tag = DI_TAG_DISPATCHER_IO)) }
    bind<GetRepositoriesByNameUseCase>() with singleton { GetRepositoriesByNameUseCase(instance(), instance(tag = DI_TAG_DISPATCHER_IO)) }
    bind<GetRepositoryDetailsByOwnerUseCase>() with singleton { GetRepositoryDetailsByOwnerUseCase(instance(), instance(tag = DI_TAG_DISPATCHER_IO)) }
    bind<GetRecentSearchUseCase>() with singleton { GetRecentSearchUseCase(instance(), instance(tag = DI_TAG_DISPATCHER_IO)) }
    bind<SetRecentSearchUseCase>() with singleton { SetRecentSearchUseCase(instance(), instance(tag = DI_TAG_SCOPE_IO)) }
}