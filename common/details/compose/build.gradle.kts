plugins {
    id("multiplatform-compose-setup")
    id("android-setup")
}

android {
    namespace = "com.sample.multiplatform.common.details.compose"
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation(project(":common:core"))
                implementation(project(":common:core-utils"))
                implementation(project(":common:details:api"))
                implementation(project(":common:details:presentation"))
                implementation(project(":common:users:api"))

                implementation(Dependencies.Other.Navigation.compose)
                implementation(Dependencies.Other.Navigation.core)
                implementation(Dependencies.Other.ViewModel.compose)
                implementation(Dependencies.Other.ViewModel.core)

                implementation(Dependencies.Image.ImageLoader.loader)
            }
        }
    }
}