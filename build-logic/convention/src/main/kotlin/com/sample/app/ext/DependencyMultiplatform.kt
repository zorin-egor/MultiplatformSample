package com.sample.app.ext

import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

internal fun Project.configureDependencyMultiplatform(
    extension: KotlinMultiplatformExtension
) = extension.apply {
    sourceSets.apply {
        commonMain.dependencies {
            implementation(getLibrary("kotlinx.coroutines.core"))
            implementation(getLibrary("kotlinx.serialization"))
            implementation(getLibrary("kodein"))
            implementation(getLibrary("kotlinx.datetime"))
        }

        androidMain.dependencies {
            implementation(getLibrary("kotlinx.coroutines.android"))
        }

        jvmMain.dependencies {
            implementation(getLibrary("kotlinx.coroutines.swing"))
        }
    }
}