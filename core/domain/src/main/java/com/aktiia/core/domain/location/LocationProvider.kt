package com.aktiia.core.domain.location

interface LocationProvider {
    suspend fun getLocation(): String
    fun checkPermission(): Boolean
    fun hasLocationEnabled(): Boolean
}