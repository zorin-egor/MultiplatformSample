package com.sample.multiplatform

import com.sample.multiplatform.json.serializationModule
import com.sample.multiplatform.ktor.ktorModule
import com.sample.multiplatform.settings.settingsModule
import org.kodein.di.DI

val coreModule = DI.Module("coreModule") {
    importAll(
        serializationModule,
        ktorModule,
        settingsModule
    )
}