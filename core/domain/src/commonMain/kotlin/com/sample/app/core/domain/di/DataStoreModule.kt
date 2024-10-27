package com.sample.app.core.domain.di

import com.sample.app.core.domain.GetUserDetailsUseCase
import com.sample.app.core.domain.GetUsersUseCase
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.provider
import org.kodein.di.singleton

const val MODULE_DOMAIN = "domainModule"

val domainModule = DI.Module(MODULE_DOMAIN) {
    bind<GetUsersUseCase>() with provider { GetUsersUseCase(instance()) }
    bind<GetUserDetailsUseCase>() with singleton { GetUserDetailsUseCase(instance()) }
}