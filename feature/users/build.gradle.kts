plugins {
    alias(libs.plugins.multiplatformSetupComposeLibrary)
}

android {
    namespace = "com.sample.app.feature.users"
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

compose.resources {
    publicResClass = true
    packageOfResClass = "com.sample.app.feature.users.resources"
    generateResClass = auto
}