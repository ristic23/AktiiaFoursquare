package com.aktiia.core.domain.location

interface LocationProvider {
    suspend fun getLocation(): String
}