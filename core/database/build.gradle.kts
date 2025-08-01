plugins {
    alias(libs.plugins.aktiia.android.library)
    alias(libs.plugins.aktiia.android.room)
}

android {
    namespace = "com.aktiia.core.database"
}

dependencies {
    implementation(projects.core.domain)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
}