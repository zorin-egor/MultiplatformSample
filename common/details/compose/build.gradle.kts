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
            }
        }
    }
}