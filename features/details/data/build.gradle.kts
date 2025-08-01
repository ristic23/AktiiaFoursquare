plugins {
    alias(libs.plugins.aktiia.android.library)
    alias(libs.plugins.aktiia.jvm.retrofit)
}

android {
    namespace = "com.aktiia.features.details.data"
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
}