pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Multiplatform"
include(":androidApp")
include(":common:core")
include(":common:platform-provider-core")

include(":common:users:api")
include(":common:users:presentation")
include(":common:users:data")
include(":common:users:compose")