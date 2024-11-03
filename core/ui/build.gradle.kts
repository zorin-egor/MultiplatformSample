plugins {
    alias(libs.plugins.multiplatformSetupComposeLibrary)
}

android {
    namespace = "com.sample.app.core.ui"
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.model)
            api(libs.compose.constraintlayout)
            api(libs.kotlinx.collections.immutable)
        }

        androidMain.dependencies {
            implementation(projects.core.model)
        }

        iosMain.dependencies {
            implementation(projects.core.model)
        }

        jsMain.dependencies {
            implementation(projects.core.model)
        }

        jvmMain.dependencies {
            implementation(projects.core.model)
        }
    }
}