plugins {
    kotlin("multiplatform")
    kotlin("kapt")
    id("com.android.library")
}

kotlin {
    jvm("desktop")
    android()
    ios()

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = "17"
    }
}