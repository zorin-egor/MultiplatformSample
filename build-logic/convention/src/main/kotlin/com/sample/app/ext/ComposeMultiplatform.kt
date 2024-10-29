package com.sample.app.ext

import org.gradle.api.Project
import org.jetbrains.compose.ComposeExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

internal fun Project.configureComposeMultiplatform(
    multiplatformExtension: KotlinMultiplatformExtension,
    composeExtension: ComposeExtension
) = multiplatformExtension.apply {
    val compose = composeExtension.dependencies
    sourceSets.apply {
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(getLibrary("androidx-lifecycle-viewmodel"))
            implementation(getLibrary("androidx.lifecycle.runtime.compose"))
            implementation(getLibrary("androidx.navigation"))

            implementation(getLibrary("coil"))
            implementation(getLibrary("coil.compose"))
            implementation(getLibrary("coil.network.ktor3"))
        }

        androidMain.dependencies {
            implementation(compose.preview)
            implementation(getLibrary("androidx.activity.compose"))
            implementation(getLibrary("coil.network.okhttp"))
        }

        iosMain.dependencies {
            implementation(getLibrary("coil.network.ktor3"))
        }

        jsMain.dependencies {
            implementation(compose.html.core)
            implementation(getLibrary("coil.network.ktor3"))
        }

        jvmMain.dependencies {
            implementation(compose.desktop.currentOs)
        }
    }

}