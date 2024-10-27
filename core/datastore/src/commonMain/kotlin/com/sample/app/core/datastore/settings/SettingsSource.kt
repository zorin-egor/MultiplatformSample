package com.sample.app.core.datastore.settings


interface SettingsSource {

    fun setToken(value: String)

    fun getToken(): String

    fun setSinceUser(value: Long)

    fun getSinceUser(): Long

}