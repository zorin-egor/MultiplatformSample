pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
    plugins {
        id("app.cash.sqldelight") version "2.0.1"
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
include(":common:widget-cycloid")
include(":common:platform-provider-core")
include(":common:platform-provider-compose")

include(":common:splash:api")
include(":common:splash:presentation")
include(":common:splash:data")
include(":common:splash:compose")

include(":common:users:api")
include(":common:users:presentation")
include(":common:users:data")
include(":common:users:compose")

include(":common:details:api")
include(":common:details:presentation")
include(":common:details:data")
include(":common:details:compose")