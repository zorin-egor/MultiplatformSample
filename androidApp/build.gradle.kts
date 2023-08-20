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
        kotlinCompilerExtensionVersion = Dependencies.Compose.compilerVersion
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

//    kotlinOptions {
//        jvmTarget = "11"
//    }

    namespace = "com.sample.multiplatform.android"
}

dependencies {
    implementation(project(":common:core"))
    implementation(project(":common:platform-provider-core"))
    implementation(project(":common:platform-provider-compose"))

    implementation(Dependencies.Android.composeActivity)
//    implementation(Dependencies.Compose.compiler)
}