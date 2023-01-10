package com.sample.multiplatform.android

import android.app.Application
import com.sample.multiplatform.PlatformConfiguration
import com.sample.multiplatform.PlatformProviderSDK

class MultiplatformApp : Application() {

    override fun onCreate() {
        super.onCreate()
        initPlatformSDK()
    }
}

fun MultiplatformApp.initPlatformSDK() =
    PlatformProviderSDK.init(
        configuration = PlatformConfiguration(context = applicationContext)
    )