plugins {
    id("multiplatform-setup")
    id("android-setup")
    kotlin("plugin.serialization")
    id("app.cash.sqldelight").version(Dependencies.SqlDelight.version)
}
android {
    namespace = "com.sample.multiplatform.common.details.data"
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation(project(":common:core"))
                implementation(project(":common:details:api"))

                implementation(Dependencies.Kodein.core)
                implementation(Dependencies.Settings.core)
            }
        }
    }
}

sqldelight {
    databases {
        create("Database") {
            packageName = "com.sample.multiplatform.details.data"
            verifyMigrations = true
            dependency(project(":common:core"))
        }
    }
}