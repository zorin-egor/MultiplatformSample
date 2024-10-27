plugins {
    alias(libs.plugins.multiplatformSetupLibrary)
}

android {
    namespace = "com.sample.app.core.datastore"
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.multiplatform.settings)
            implementation(libs.multiplatform.settings.noarg)
        }
    }
}