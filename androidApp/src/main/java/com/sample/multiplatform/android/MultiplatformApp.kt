package com.sample.multiplatform.android

import android.app.Application
import com.sample.multiplatform.PlatformConfiguration
import com.sample.multiplatform.PlatformSDK

class MultiplatformApp : Application() {

    override fun onCreate() {
        super.onCreate()
        initPlatformSDK()
    }
}

fun MultiplatformApp.initPlatformSDK() =
    PlatformSDK.init(
        configuration = PlatformConfiguration(context = applicationContext)
    )