plugins {
    id("multiplatform-setup")
    id("android-setup")
    kotlin("plugin.serialization")
}

android {
    namespace = "com.sample.multiplatform.common.users.data"
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation(project(":common:users:api"))
                implementation(project(":common:core"))

                implementation(Dependencies.Kodein.core)
                implementation(Dependencies.Settings.core)
            }
        }
    }
}