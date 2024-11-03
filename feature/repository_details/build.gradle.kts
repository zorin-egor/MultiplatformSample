plugins {
    alias(libs.plugins.multiplatformSetupComposeLibrary)
}

android {
    namespace = "com.sample.app.feature.repository_details"
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.domain)
            implementation(projects.core.model)
            implementation(projects.core.ui)
        }
    }
}