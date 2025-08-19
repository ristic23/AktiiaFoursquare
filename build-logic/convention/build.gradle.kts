plugins {
    `kotlin-dsl`
}

group = "com.aktiia.buildlogic"

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.android.tools.common)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
    compileOnly(libs.room.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "aktiia.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidApplicationCompose") {
            id = "aktiia.android.application.compose"
            implementationClass = "AndroidApplicationComposeConventionPlugin"
        }
        register("androidLibrary") {
            id = "aktiia.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("androidLibraryCompose") {
            id = "aktiia.android.library.compose"
            implementationClass = "AndroidLibraryComposeConventionPlugin"
        }
        register("androidFeatureUI") {
            id = "aktiia.android.feature.ui"
            implementationClass = "AndroidFeatureUIConventionPlugin"
        }
        register("androidRoom") {
            id = "aktiia.android.room"
            implementationClass = "AndroidRoomConventionPlugin"
        }
        register("jvmLibrary") {
            id = "aktiia.jvm.library"
            implementationClass = "JvmLibraryConventionPlugin"
        }
        register("jvmRetrofit") {
            id = "aktiia.jvm.retrofit"
            implementationClass = "JvmRetrofitConventionPlugin"
        }
        register("jvmJunit5") {
            id = "aktiia.jvm.junit5"
            implementationClass = "JvmJUnit5ConventionPlugin"
        }
        register("androidJunit5") {
            id = "aktiia.android.junit5"
            implementationClass = "AndroidJUnit5ConventionPlugin"
        }
        register("appFlavors") {
            id = "aktiia.app.flavors"
            implementationClass = "AndroidFlavorsConventionalPlugin"
        }
    }
}