plugins {
    id("multiplatform-setup")
    id("multiplatform-compose-setup")
    id("android-setup")
}

android {
    namespace = "com.sample.multiplatform.common.core_utils"
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation(Dependencies.Kotlin.Coroutines.core)
            }
        }
    }
}
