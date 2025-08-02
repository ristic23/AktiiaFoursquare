plugins {
    alias(libs.plugins.aktiia.android.library)
    alias(libs.plugins.aktiia.android.room)
}

android {
    namespace = "com.aktiia.core.database"
}

dependencies {
    implementation(projects.core.domain)
    implementation(projects.features.search.domain)
    implementation(projects.features.favorites.domain)
    implementation(projects.features.details.domain)

    implementation(libs.bundles.koin)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
}