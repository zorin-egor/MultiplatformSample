plugins {
    id("multiplatform-setup")
    id("android-setup")
}
android {
    namespace = "com.sample.multiplatform.common.platform_provider_core"
}

//dependencies {
//    implementation(project(mapOf("path" to ":common:users:api")))
//}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation(project(":common:core"))
                implementation(project(":common:users:data"))
                implementation(project(":common:users:presentation"))
                implementation(project(":common:details:data"))
                implementation(Dependencies.Kodein.core)
                implementation(Dependencies.Image.ImageLoader.loader)
            }
        }
    }
}