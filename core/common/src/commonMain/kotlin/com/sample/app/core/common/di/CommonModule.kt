package com.sample.app.core.common.di

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

const val DI_MODULE_COMMON = "commonModule"
const val DI_TAG_DISPATCHER_IO = "coroutine_dispatcher_io"
const val DI_TAG_DISPATCHER_COMPUTE = "coroutine_dispatcher_compute"
const val DI_TAG_SCOPE_IO = "coroutine_scope_io"
const val DI_TAG_SCOPE_COMPUTE = "coroutine_scope_compute"

val commonModule = DI.Module(DI_MODULE_COMMON) {
    bind<CoroutineDispatcher>(tag = DI_TAG_DISPATCHER_IO) with singleton {
        Dispatchers.Default.limitedParallelism(parallelism = 64, name = DI_TAG_DISPATCHER_IO)
    }
    bind<CoroutineDispatcher>(tag = DI_TAG_DISPATCHER_COMPUTE) with singleton {
        Dispatchers.Default
    }
    bind<CoroutineScope>(tag = DI_TAG_SCOPE_IO) with singleton {
        CoroutineScope(instance(tag = DI_TAG_DISPATCHER_IO))
    }
    bind<CoroutineScope>(tag = DI_TAG_SCOPE_COMPUTE) with singleton {
        CoroutineScope(instance(tag = DI_TAG_DISPATCHER_COMPUTE))
    }
}