plugins {
    id("multiplatform-setup")
    id("android-setup")
    kotlin("plugin.serialization")
    id("app.cash.sqldelight").version(Dependencies.SqlDelight.version)
}

android {
    namespace = "com.sample.multiplatform.common.users.data"
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation(project(":common:core"))
                implementation(project(":common:users:api"))

                implementation(Dependencies.Kodein.core)
                implementation(Dependencies.Settings.core)
            }
        }
    }
}

sqldelight {
    databases {
        create("Database") {
            packageName = "com.sample.multiplatform.users.data"
            verifyMigrations = true
            dependency(project(":common:core"))
        }
    }
}