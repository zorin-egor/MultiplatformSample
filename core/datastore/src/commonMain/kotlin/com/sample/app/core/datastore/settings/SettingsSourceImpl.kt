package com.sample.app.core.datastore.settings

import com.russhwolf.settings.Settings
import com.russhwolf.settings.get

internal class SettingsSourceImpl(
    private val settings: Settings
) : SettingsSource {

    override fun setToken(value: String) = settings.putString(KEY_TOKEN, value)

    override fun getToken(): String = settings[KEY_TOKEN, ""]

    override fun setSinceUser(value: Long) = settings.putLong(KEY_SINCE, value)

    override fun getSinceUser(): Long = settings[KEY_SINCE, 0L]

    companion object {
        private const val KEY_TOKEN = "KEY_TOKEN"
        private const val KEY_SINCE = "KEY_SINCE"
    }
}