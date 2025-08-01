pluginManagement {
    includeBuild("build-logic")
    repositories {
        google ()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "AktiiaFoursquare"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
include(":app")

include(":features:search:data")
include(":features:search:presentation")
include(":features:search:domain")

include(":features:details:data")
include(":features:details:presentation")
include(":features:details:domain")

include(":features:favorites:data")
include(":features:favorites:presentation")
include(":features:favorites:domain")
include(":core:presentation:designsystem")
include(":core:presentation:ui")
include(":core:domain")
include(":core:data")
include(":core:database")
include(":features:details:network")
include(":features:favorites:network")
include(":features:search:network")
