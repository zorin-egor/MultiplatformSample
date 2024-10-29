import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    alias(libs.plugins.multiplatformSetupLibrary)
    alias(libs.plugins.sqldelight)
}

android {
    namespace = "com.sample.app.core.data"
}

sqldelight {
    databases {
        create("Database") {
            packageName.set("com.sample.app.core.data")
            generateAsync.set(true)
        }
    }
    linkSqlite = true
}

kotlin {
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            linkerOpts.add("-lsqlite3")
            isStatic = false
        }
    }

    targets.withType<KotlinNativeTarget> {
        binaries {
            all {
                linkerOpts("-lsqlite3")
            }
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.sqldelight.coroutines.ext)
            api(projects.core.common)
            api(projects.core.network)
            api(projects.core.model)
            api(projects.core.datastore)
        }

        androidMain.dependencies {
            implementation(libs.sqldelight.android.driver)
            api(projects.core.common)
        }

        iosMain.dependencies {
            implementation(libs.sqldelight.native.driver)
            api(projects.core.common)
        }

        jsMain.dependencies {
            implementation(libs.sqldelight.web.worker.driver)
            implementation(npm("sql.js", "1.11.0"))
            implementation(npm("@cashapp/sqldelight-sqljs-worker", "2.0.2"))
            implementation(devNpm("copy-webpack-plugin", "12.0.2"))
            api(projects.core.common)
        }

        jvmMain.dependencies {
            implementation(libs.sqldelight.sqlite.driver)
            api(projects.core.common)
        }
    }
}