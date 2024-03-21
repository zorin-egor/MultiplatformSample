plugins {
    id("multiplatform-setup")
    id("multiplatform-compose-setup")
    id("android-setup")
}

android {
    namespace = "com.sample.multiplatform.common.splash.compose"
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation(project(":common:core"))
                implementation(project(":common:core-utils"))
                implementation(project(":common:core-compose"))
                implementation(project(":common:splash:api"))
                implementation(project(":common:splash:presentation"))
                implementation(project(":common:users:api"))
                implementation(project(":common:widget-cycloid"))

                implementation(Dependencies.Other.Navigation.compose)
                implementation(Dependencies.Other.Navigation.core)
                implementation(Dependencies.Other.ViewModel.compose)
                implementation(Dependencies.Other.ViewModel.core)
                implementation(Dependencies.Other.ViewModel.odyssey)
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