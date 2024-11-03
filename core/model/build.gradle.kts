plugins {
    alias(libs.plugins.multiplatformSetupLibrary)
}

android {
    namespace = "com.sample.app.core.model"
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.io.core)
        }
    }
}