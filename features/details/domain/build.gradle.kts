plugins {
    alias(libs.plugins.aktiia.jvm.library)
}

dependencies {
    implementation(projects.core.domain)

    implementation(libs.kotlinx.coroutines.core)
}