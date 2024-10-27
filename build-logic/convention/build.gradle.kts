import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
}

group = "com.sample.app.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

dependencies {
    compileOnly(libs.plugins.kotlinMultiplatform.toDep())
    compileOnly(libs.plugins.androidApplication.toDep())
    compileOnly(libs.plugins.androidLibrary.toDep())
    compileOnly(libs.plugins.jetbrainsCompose.toDep())
    compileOnly(libs.plugins.composeCompiler.toDep())
    compileOnly(libs.plugins.kotlinSerialization.toDep())
    compileOnly(libs.plugins.ksp.toDep())
    compileOnly(libs.plugins.sqldelight.toDep())
}

fun Provider<PluginDependency>.toDep() = map {
    "${it.pluginId}:${it.pluginId}.gradle.plugin:${it.version}"
}

tasks {
    validatePlugins {
        enableStricterValidation = true
        failOnWarning = true
    }
}

gradlePlugin {
    plugins {
        register("multiplatformLibrarySetup") {
            id = "com.multiplatform.setup.library"
            implementationClass = "MultiplatformLibrarySetup"
        }
        register("multiplatformApplicationSetup") {
            id = "com.multiplatform.setup.app"
            implementationClass = "MultiplatformApplicationSetup"
        }
//        register("shared") {
//            id = "com.razzaghi.shopingbykmp.shared"
//            implementationClass = "SharedConventionPlugin"
//        }
//        register("androidApp") {
//            id = "com.razzaghi.shopingbykmp.androidApp"
//            implementationClass = "AndroidAppConventionPlugin"
//        }
    }
}