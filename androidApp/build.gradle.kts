plugins {
    id("com.android.application")
    id("org.jetbrains.compose")
    kotlin("android")
}

android {
    compileSdk = 33
    defaultConfig {
        applicationId = "com.sample.multiplatform.android"
        minSdk = 21
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.3.2"
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
    
    namespace = "com.sample.multiplatform.android"
}

dependencies {
    implementation(project(":common:core"))
    implementation(project(":common:platform-provider-core"))
    implementation(project(":common:platform-provider-compose"))

    implementation("androidx.activity:activity-compose:1.6.1")
}