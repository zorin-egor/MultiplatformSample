plugins {
    id("multiplatform-setup")
    id("android-setup")
}

android {
    namespace = "com.sample.multiplatform.common.details.presentation"
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                api(project(":common:core"))
                implementation(project(":common:users:api"))
                implementation(project(":common:details:api"))
                implementation(Dependencies.Other.ViewModel.core)
            }
        }
    }
}