plugins {
    alias(libs.plugins.aktiia.android.library)
    alias(libs.plugins.aktiia.jvm.retrofit)
}

android {
    namespace = "com.aktiia.features.search.data"
}

dependencies {
    implementation(projects.features.search.domain)
    implementation(projects.core.domain)
    implementation(projects.core.data)
    implementation(projects.core.database)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
}