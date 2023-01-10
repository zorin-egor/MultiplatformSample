package com.sample.multiplatform.settings

import com.russhwolf.settings.Settings
import com.russhwolf.settings.get

class SettingsDetailsSource(
    private val settings: Settings
) {

    fun saveSince(value: Long) {
        settings.putLong(sinceKey, value)
    }

    fun fetchSince(): Long {
        return settings[sinceKey, 0L]
    }

    companion object {
        private val sinceKey = "sinceKey"
    }
}