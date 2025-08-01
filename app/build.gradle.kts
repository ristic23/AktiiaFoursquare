plugins {
    alias(libs.plugins.aktiia.android.application.compose)
    alias(libs.plugins.aktiia.jvm.retrofit)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.aktiia.foursquare"

    defaultConfig {

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }
}

dependencies {
    implementation(projects.core.data)
    implementation(projects.core.database)
    implementation(projects.core.domain)
    implementation(projects.core.presentation.ui)
    implementation(projects.core.presentation.designsystem)

    implementation(projects.features.details.data)
    implementation(projects.features.details.domain)
    implementation(projects.features.details.presentation)

    implementation(projects.features.favorites.data)
    implementation(projects.features.favorites.domain)
    implementation(projects.features.favorites.presentation)

    implementation(projects.features.search.data)
    implementation(projects.features.search.domain)
    implementation(projects.features.search.presentation)


    // libs
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.compose.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.test.manifest)
}