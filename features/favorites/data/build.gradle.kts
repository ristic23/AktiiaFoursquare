plugins {
    alias(libs.plugins.aktiia.android.library)
    alias(libs.plugins.aktiia.jvm.retrofit)
}

android {
    namespace = "com.aktiia.features.favorites.data"
}

dependencies {
    implementation(projects.features.favorites.domain)
    implementation(projects.core.domain)

    implementation(libs.androidx.core.ktx)
    implementation(libs.bundles.koin)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
}