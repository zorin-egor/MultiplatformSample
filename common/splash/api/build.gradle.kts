plugins {
    id("multiplatform-setup")
    id("android-setup")
    kotlin("plugin.serialization")
}
android {
    namespace = "com.sample.multiplatform.common.splash.api"
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
            }
        }
    }
}