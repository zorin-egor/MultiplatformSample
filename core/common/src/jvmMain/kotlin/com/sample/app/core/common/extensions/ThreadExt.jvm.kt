package com.sample.app.core.common.extensions

actual val currentThreadName: String
    get() = Thread.currentThread().name