plugins {
    alias(libs.plugins.multiplatformSetupLibrary)
}

android {
    namespace = "com.sample.app.core.network"
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(libs.bundles.ktor)
            implementation(projects.core.common)
            implementation(projects.core.model)
            implementation(projects.core.datastore)
        }

        androidMain.dependencies {
            api(libs.ktor.client.android)
            implementation(projects.core.common)
        }

        iosMain.dependencies {
            api(libs.ktor.client.darwin)
            implementation(projects.core.common)
        }

        jsMain.dependencies {
            api(libs.coil.network.ktor3)
            implementation(projects.core.common)
        }

        jvmMain.dependencies {
            api(libs.ktor.client.http)
            implementation(projects.core.common)
        }
    }
}