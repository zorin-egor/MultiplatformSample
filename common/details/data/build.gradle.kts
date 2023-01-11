plugins {
    id("multiplatform-setup")
    id("android-setup")
    kotlin("plugin.serialization")
}
android {
    namespace = "com.sample.multiplatform.common.details.data"
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation(project(":common:details:api"))
                implementation(project(":common:core"))

                implementation(Dependencies.Kodein.core)
                implementation(Dependencies.Settings.core)
            }
        }
    }
}