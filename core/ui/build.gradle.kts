plugins {
    alias(libs.plugins.multiplatformSetupLibrary)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.composeCompiler)
}

android {
    namespace = "com.sample.app.core.ui"
}

kotlin {
    sourceSets {
        val desktopMain by getting

        commonMain.dependencies {
            api(compose.runtime)
            api(compose.foundation)
            api(compose.material)
            api(compose.ui)
            api(compose.components.resources)
            api(compose.components.uiToolingPreview)
            api(libs.androidx.lifecycle.viewmodel)
            api(libs.androidx.lifecycle.runtime.compose)
            api(libs.androidx.navigation)

            api(libs.coil)
            api(libs.coil.compose)
            api(libs.coil.network.ktor3)

            implementation(projects.core.model)
        }

        androidMain.dependencies {
            api(compose.preview)
            api(libs.androidx.activity.compose)
            api(libs.coil.network.okhttp)
            implementation(projects.core.model)
        }

        iosMain.dependencies {
            api(libs.coil.network.ktor3)
            implementation(projects.core.model)
        }

        jsMain.dependencies {
            api(compose.html.core)
            api(libs.coil.network.ktor3)
            implementation(projects.core.model)
        }

        desktopMain.dependencies {
            api(compose.desktop.currentOs)
            implementation(projects.core.model)
        }
    }
}