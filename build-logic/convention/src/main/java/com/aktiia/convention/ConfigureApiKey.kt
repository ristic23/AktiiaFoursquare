package com.aktiia.convention

import org.gradle.api.Project

fun Project.configureApiKeys() {
    val apiKeys = System.getenv("API_KEYS")
        ?: findProperty("apiKeys") as? String
        ?: readLocalProperty("apiKeys")

    extensions.extraProperties["apiKeys"] = apiKeys
}

private fun Project.readLocalProperty(key: String): String? {
    val propertiesFile = rootProject.file("local.properties")
    if (!propertiesFile.exists()) return null

    val properties = java.util.Properties().apply {
        propertiesFile.inputStream().use { load(it) }
    }

    return properties.getProperty(key)
}