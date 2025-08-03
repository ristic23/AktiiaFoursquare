plugins {
    alias(libs.plugins.aktiia.android.library)
}

android {
    namespace = "com.aktiia.core.location"
}

dependencies {
    implementation(projects.core.domain)

    implementation(libs.androidx.core.ktx)
    implementation(libs.bundles.koin)
    implementation(libs.kotlinx.coroutines.play.services)

    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.google.android.gms.play.services.location)

    implementation(projects.core.domain)
}