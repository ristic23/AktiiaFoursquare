plugins {
    alias(libs.plugins.aktiia.android.library)
    alias(libs.plugins.aktiia.jvm.retrofit)
    alias(libs.plugins.aktiia.jvm.junit5)
}

android {
    namespace = "com.aktiia.features.details.data"
}

dependencies {
    implementation(projects.features.details.domain)
    implementation(projects.core.domain)
    implementation(projects.core.data)

    implementation(libs.androidx.core.ktx)
    implementation(libs.bundles.koin)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
}