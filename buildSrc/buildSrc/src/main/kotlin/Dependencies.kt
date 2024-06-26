object Dependencies {

    object Kodein {
        const val core = "org.kodein.di:kodein-di:7.21.0"
    }

    object Settings {
        const val core = "com.russhwolf:multiplatform-settings:1.1.1"
        const val noargs = "com.russhwolf:multiplatform-settings-no-arg:1.1.1"
    }

    object Image {
        object ImageLoader {
            const val version = "1.6.4"
            const val loader = "io.github.qdsfdhvh:image-loader:$version"
        }
    }

    object Kotlin {
        private const val version = "1.9.20"
        const val gradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$version"

        object Serialization {
            private const val version = Kotlin.version
            const val gradlePlugin = "org.jetbrains.kotlin:kotlin-serialization:$version"
            const val serialization = "org.jetbrains.kotlinx:kotlinx-serialization-core:1.5.1"
        }

        object Coroutines {
            private const val version = "1.7.3"
            const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$version"
        }
    }

    object Compose {
        private const val pluginVersion = "1.5.10"
        const val plugin = "org.jetbrains.compose:compose-gradle-plugin:$pluginVersion"
    }

    object Ktor {
        private const val version = "2.3.6"
        const val core = "io.ktor:ktor-client-core:$version"
        const val json = "io.ktor:ktor-client-json:$version"
        const val ios = "io.ktor:ktor-client-ios:$version"
        const val negotiation = "io.ktor:ktor-client-content-negotiation:$version"
        const val kotlin_json = "io.ktor:ktor-serialization-kotlinx-json:$version"
        const val serialization = "io.ktor:ktor-client-serialization:$version"
        const val logging = "io.ktor:ktor-client-logging:$version"
        const val android = "io.ktor:ktor-client-android:$version"
        const val okhttp = "io.ktor:ktor-client-okhttp:$version"
    }

    object SqlDelight {
        const val version = "2.0.1"
        const val android = "app.cash.sqldelight:android-driver:$version"
        const val desktop = "app.cash.sqldelight:sqlite-driver:$version"
        const val ios = "app.cash.sqldelight:native-driver:$version"
        const val coroutines = "app.cash.sqldelight:coroutines-extensions:$version"
    }

    object Android {
        const val gradlePlugin = "com.android.tools.build:gradle:8.1.4"
        const val composeActivity = "androidx.activity:activity-compose:1.8.1"

        object Compose {
            const val compiler = "androidx.compose.compiler:compiler:1.5.4"
            const val runtime = "androidx.compose.runtime:runtime:1.5.4"
            const val ui = "androidx.compose.ui:ui:1.5.4"
            const val material = "androidx.compose.material:material:1.5.4"
            const val tooling = "androidx.compose.ui:ui-tooling:1.5.4"
            const val icons = "androidx.compose.material:material-icons-core:1.5.4"
        }
    }

    object Other {
        object ViewModel {
            private const val version = "0.15"
            const val core = "com.adeo:kviewmodel:$version"
            const val compose = "com.adeo:kviewmodel-compose:$version"
            const val odyssey = "com.adeo:kviewmodel-odyssey:$version"
        }

        object Navigation {
            private const val version = "1.3.40"
            const val core = "io.github.alexgladkov:odyssey-core:$version"
            const val compose = "io.github.alexgladkov:odyssey-compose:$version"
        }
    }

}