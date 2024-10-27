import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.multiplatformSetupApp)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.sqldelight)
}

//sqldelight {
//    databases {
//        create("Database") {
//            packageName = "com.sample.app"
//            verifyMigrations = true
//            dependency(projects.core.data)
//        }
//    }
//}

kotlin {
    jvmToolchain(17)
    jvm("desktop")

    js {
        moduleName = "composeApp"
        browser {
            commonWebpackConfig {
                outputFileName = "composeApp.js"
            }
        }
        binaries.executable()
    }

    androidTarget()

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = false
        }
    }

    sourceSets {
        val desktopMain by getting

        commonMain.dependencies {
            implementation(projects.core.ui)
            implementation(projects.core.domain)
            implementation(projects.feature.users)
            implementation(projects.feature.details)
            implementation(compose.components.resources)
        }

        androidMain.dependencies {
            implementation(projects.core.ui)
            implementation(projects.core.domain)
        }

        iosMain.dependencies {
            implementation(projects.core.ui)
            implementation(projects.core.domain)
        }

        jsMain.dependencies {
            implementation(devNpm("copy-webpack-plugin", "12.0.2"))
            implementation(projects.core.ui)
            implementation(projects.core.domain)
        }

        desktopMain.dependencies {
            implementation(projects.core.ui)
            implementation(projects.core.domain)
            implementation(libs.kotlinx.coroutines.swing)
        }
    }
}

android {
    namespace = "com.sample.app"

    defaultConfig {
        applicationId = "com.sample.app"
        versionCode = 1
        versionName = "1.0"
    }
}

compose.desktop {
    application {
        mainClass = "com.sample.app.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Exe, TargetFormat.Deb)
            packageName = "com.sample.app.desktopApp"
            packageVersion = "1.0.0"
        }
    }
}

dependencies {
    debugImplementation(compose.uiTooling)
}

