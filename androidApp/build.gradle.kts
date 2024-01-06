plugins {
    id("com.android.application")
    kotlin("android")
    id("org.jetbrains.compose")
}

android {
    compileSdk = 34
    defaultConfig {
        applicationId = "com.sample.multiplatform.android"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }

    buildFeatures {
        compose = true
    }

    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
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

    namespace = "com.sample.multiplatform.android"
}

dependencies {
    implementation(project(":common:core"))
    implementation(project(":common:platform-provider-core"))
    implementation(project(":common:platform-provider-compose"))

    implementation(Dependencies.Android.Compose.runtime)
    implementation(Dependencies.Android.composeActivity)
}