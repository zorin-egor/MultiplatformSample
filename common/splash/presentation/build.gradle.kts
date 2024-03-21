plugins {
    id("multiplatform-setup")
    id("android-setup")
}

android {
    namespace = "com.sample.multiplatform.common.splash.presentation"
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation(project(":common:core"))
                implementation(project(":common:core-utils"))
                implementation(project(":common:users:api"))
                implementation(Dependencies.Other.ViewModel.core)
            }
        }
    }
}