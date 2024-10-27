package com.sample.app.core.datastore.di

import com.russhwolf.settings.Settings
import com.sample.app.core.datastore.settings.SettingsSource
import com.sample.app.core.datastore.settings.SettingsSourceImpl
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

const val MODULE_DATA_STORE = "dataStoreModule"

val dataStoreModule = DI.Module(MODULE_DATA_STORE) {
    bind<Settings>() with singleton { Settings() }
    bind<SettingsSource>() with singleton { SettingsSourceImpl(instance()) }
}