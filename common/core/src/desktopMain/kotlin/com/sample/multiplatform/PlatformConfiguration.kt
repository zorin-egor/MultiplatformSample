package com.sample.multiplatform

actual class PlatformConfiguration {
    val appDataPath = when {
        System.getProperty("os.name").contains("Mac", true) -> "/Users/${System.getProperty("user.name")}/Library/Application Support/appName"
        System.getProperty("os.name").contains("windows", true) -> "${System.getProperty("user.home")}\\AppData\\Local\\appName"
        else -> throw IllegalStateException("This type OS not implemented")
    }
}