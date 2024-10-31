import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.multiplatformSetupApp)
    alias(libs.plugins.sqldelight)
}

kotlin {
    jvm()

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
        commonMain.dependencies {
            implementation(projects.core.ui)
            implementation(projects.core.domain)
            implementation(projects.feature.splash)
            implementation(projects.feature.users)
            implementation(projects.feature.userDetails)
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
            implementation(projects.core.ui)
            implementation(projects.core.domain)
        }

        jvmMain.dependencies {
            implementation(projects.core.ui)
            implementation(projects.core.domain)
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

