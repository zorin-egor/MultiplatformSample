plugins {
    alias(libs.plugins.multiplatformSetupLibrary)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.composeCompiler)
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

//        androidMain.dependencies {
//            implementation(projects.core.domain)
//            implementation(projects.core.model)
//            implementation(projects.core.ui)
//        }
//
//        iosMain.dependencies {
//            implementation(projects.core.domain)
//            implementation(projects.core.model)
//            implementation(projects.core.ui)
//        }
//
//        jsMain.dependencies {
//            implementation(projects.core.domain)
//            implementation(projects.core.model)
//            implementation(projects.core.ui)
//        }
//
//        desktopMain.dependencies {
//            implementation(projects.core.domain)
//            implementation(projects.core.model)
//            implementation(projects.core.ui)
//        }
    }
}