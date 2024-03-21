plugins {
    id("multiplatform-setup")
    id("android-setup")
    kotlin("plugin.serialization")
    id("app.cash.sqldelight").version(Dependencies.SqlDelight.version)
}

android {
    namespace = "com.sample.multiplatform.common.core"
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                api(Dependencies.Kotlin.Serialization.serialization)
                api(Dependencies.Kotlin.Coroutines.core)
                api(Dependencies.Ktor.core)
                api(Dependencies.Kodein.core)

                implementation(Dependencies.Ktor.json)
                implementation(Dependencies.Ktor.serialization)
                implementation(Dependencies.Ktor.negotiation)
                implementation(Dependencies.Ktor.kotlin_json)
                implementation(Dependencies.Ktor.logging)
                implementation(Dependencies.Settings.core)
                implementation(Dependencies.Settings.noargs)
                implementation(Dependencies.Other.ViewModel.core)
                implementation(Dependencies.Image.ImageLoader.loader)
                implementation(Dependencies.SqlDelight.coroutines)
            }
        }

        androidMain {
            dependencies {
                implementation(Dependencies.Ktor.android)
                implementation(Dependencies.SqlDelight.android)
            }
        }

        iosMain {
            dependencies {
                implementation(Dependencies.Ktor.ios)
                implementation(Dependencies.SqlDelight.ios)
            }
        }

        desktopMain {
            dependencies {
                implementation(Dependencies.Ktor.okhttp)
                implementation(Dependencies.SqlDelight.desktop)
            }
        }
    }
}

sqldelight {
    databases {
        create("Database") {
            packageName = "com.sample.multiplatform.core"
            verifyMigrations = true
        }
//        linkSqlite = false
    }
}