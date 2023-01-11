plugins {
    id("multiplatform-setup")
    id("android-setup")
    kotlin("plugin.serialization")
}
android {
    namespace = "com.sample.multiplatform.common.details.api"
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation(Dependencies.Kotlin.Serialization.serialization)
            }
        }
    }
}