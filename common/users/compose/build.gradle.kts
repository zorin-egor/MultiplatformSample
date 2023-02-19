plugins {
    id("multiplatform-setup")
    id("multiplatform-compose-setup")
    id("android-setup")
}

android {
    namespace = "com.sample.multiplatform.common.users.compose"
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation(project(":common:core"))
                implementation(project(":common:core-utils"))
                implementation(project(":common:users:api"))
                implementation(project(":common:users:presentation"))
                implementation(project(":common:details:api"))

                implementation(Dependencies.Other.Navigation.compose)
                implementation(Dependencies.Other.Navigation.core)
                implementation(Dependencies.Other.ViewModel.compose)
                implementation(Dependencies.Other.ViewModel.core)
            }
        }
        androidMain {
            dependencies {
            }
        }

        iosMain {
            dependencies {
            }
        }

        desktopMain {
            dependencies {
            }
        }
    }
}