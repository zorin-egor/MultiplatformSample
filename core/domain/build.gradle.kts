plugins {
    alias(libs.plugins.multiplatformSetupLibrary)
}

android {
    namespace = "com.sample.app.core.domain"
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.core.data)
            api(projects.core.model)
        }
    }
}