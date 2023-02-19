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
include(":desktop")

include(":common:core")
include(":common:core-utils")
include(":common:core-compose")
include(":common:platform-provider-core")
include(":common:platform-provider-compose")

include(":common:users:api")
include(":common:users:presentation")
include(":common:users:data")
include(":common:users:compose")

include(":common:details:api")
include(":common:details:presentation")
include(":common:details:data")
include(":common:details:compose")