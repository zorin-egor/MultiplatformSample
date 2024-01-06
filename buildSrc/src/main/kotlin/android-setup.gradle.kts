plugins {
    id("com.android.library")
    kotlin("multiplatform")
}

android {
    compileSdk = 34

    defaultConfig {
        minSdk = 24
        targetSdk = 34
    }

    buildFeatures {
        compose = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.4"
    }

    kotlin {
        jvmToolchain(17)
    }

    sourceSets {
        named("main") {
            manifest.srcFile("src/androidMain/AndroidManifest.xml")
            res.srcDirs("src/androidMain/res", "src/commonMain/resources")
        }
    }

    dependencies {
        implementation(Dependencies.Android.Compose.runtime)
    }
}

