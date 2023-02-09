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
                implementation(project(":common:users:api"))
                implementation(project(":common:details:api"))
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