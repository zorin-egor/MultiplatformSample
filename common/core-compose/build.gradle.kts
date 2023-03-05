plugins {
    id("multiplatform-compose-setup")
    id("android-setup")
}

android {
    namespace = "com.sample.multiplatform.common.core_compose"
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {}
        }
    }
}