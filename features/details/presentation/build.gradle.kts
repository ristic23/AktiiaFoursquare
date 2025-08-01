plugins {
    alias(libs.plugins.aktiia.android.feature.ui)
}

android {
    namespace = "com.aktiia.features.details.presentation"
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
}