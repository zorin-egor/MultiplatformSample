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