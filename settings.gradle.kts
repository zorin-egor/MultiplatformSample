rootProject.name = "MultiplatformSample"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        mavenLocal()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        mavenLocal()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

include(":core:common")
include(":core:model")
include(":core:datastore")
include(":core:network")
include(":core:data")
include(":core:domain")
include(":core:ui")

include(":feature:splash")
include(":feature:users")
include(":feature:user_details")
include(":feature:repositories")
include(":feature:repository_details")

include(":widgets:widget-cycloid")

include(":composeApp")