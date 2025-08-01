plugins {
    alias(libs.plugins.aktiia.android.library.compose)
}

android {
    namespace = "com.aktiia.core.presentation.designsystem"
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
}