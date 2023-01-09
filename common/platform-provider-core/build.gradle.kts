plugins {
    id("multiplatform-setup")
    id("android-setup")
}
dependencies {
    implementation(project(mapOf("path" to ":common:users:api")))
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation(project(":common:core"))
                implementation(project(":common:users:data"))
                implementation(project(":common:users:api"))

                implementation(Dependencies.Kodein.core)
            }
        }
    }
}