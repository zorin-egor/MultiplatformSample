package com.sample.app.ext

import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

internal fun Project.configureKotlinMultiplatform(
    extension: KotlinMultiplatformExtension
) = extension.apply {

    jvmToolchain(17)
    jvm("desktop")

    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }

    js {
        browser()
        nodejs()
    }

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    applyDefaultHierarchyTemplate()

    sourceSets.apply {
        commonMain.dependencies {
            api(libs.findLibrary("kotlinx.coroutines.core").get())
            api(libs.findLibrary("kotlinx.serialization").get())
            api(libs.findLibrary("kodein").get())
        }

        androidMain.dependencies {
            api(libs.findLibrary("kotlinx.coroutines.android").get())
        }

        jvmMain.dependencies {
            api(libs.findLibrary("kotlinx.coroutines.swing").get())
        }
    }
}