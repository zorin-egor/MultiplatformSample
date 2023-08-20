plugins {
    id("com.android.library")
    kotlin("multiplatform")
    id("org.jetbrains.compose")
}

//compose {
//    kotlinCompilerPlugin.set(Dependencies.Compose.compilerVersion)
//}

kotlin {
    jvm("desktop")
    android()
    ios()

    sourceSets {
        named("commonMain") {
            dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material)
                implementation(compose.ui)
                implementation(compose.animation)
//                implementation(Dependencies.Compose.compiler)
            }
        }

        named("desktopMain") {
            dependencies {}
        }

        named("androidMain") {
            dependencies {}
        }
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = "11"
    }
}