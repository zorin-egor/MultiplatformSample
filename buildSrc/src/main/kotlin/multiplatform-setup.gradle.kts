plugins {
    kotlin("multiplatform")
    kotlin("kapt")
    id("com.android.library")
}

kotlin {
    jvm("desktop")
    androidTarget()
    iosArm64()
    iosX64()

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = "17"
    }
}