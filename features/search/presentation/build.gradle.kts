plugins {
    alias(libs.plugins.aktiia.android.feature.ui)
}

android {
    namespace = "com.aktiia.features.search.presentation"
}

dependencies {
    implementation(projects.features.search.domain)
    implementation(projects.core.domain)

    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
}