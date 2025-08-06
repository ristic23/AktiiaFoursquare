import java.util.Properties

fun getApiKey(): String {
    val localPropertiesFile = rootProject.file("local.properties")
    if (localPropertiesFile.exists()) {
        val properties = Properties()
        properties.load(localPropertiesFile.inputStream())
        return properties.getProperty("API_KEY")
            ?: throw GradleException("API_KEY not found in local.properties")
    }
    throw GradleException("local.properties file not found")
}

plugins {
    alias(libs.plugins.aktiia.android.application.compose)
    alias(libs.plugins.aktiia.jvm.retrofit)
}

android {
    namespace = "com.aktiia.foursquare"

    defaultConfig {

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        buildConfigField("String", "API_KEY", "\"${getApiKey()}\"")
    }
}

dependencies {
    implementation(projects.core.data)
    implementation(projects.core.database)
    implementation(projects.core.domain)
    implementation(projects.core.presentation.designsystem)
    implementation(projects.core.location)

    implementation(projects.features.details.data)
    implementation(projects.features.details.domain)
    implementation(projects.features.details.presentation)

    implementation(projects.features.favorites.data)
    implementation(projects.features.favorites.domain)
    implementation(projects.features.favorites.presentation)

    implementation(projects.features.search.data)
    implementation(projects.features.search.domain)
    implementation(projects.features.search.presentation)


    // libs
    implementation(libs.androidx.core.ktx)
    implementation(libs.bundles.koin)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.compose.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.test.manifest)
}