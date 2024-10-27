package com.sample.app

import android.app.Application
import com.sample.app.core.model.AppConfigModel

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        PlatformProvider.init(AppConfigModel(this))
    }
}