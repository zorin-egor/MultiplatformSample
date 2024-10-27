plugins {
    alias(libs.plugins.multiplatformSetupLibrary)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.composeCompiler)
}

android {
    namespace = "com.sample.app.feature.details"
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