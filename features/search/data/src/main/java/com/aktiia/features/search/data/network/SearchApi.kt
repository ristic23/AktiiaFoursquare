package com.aktiia.features.search.data.network

import com.aktiia.features.search.data.dto.PlacesDataDto
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApi {
    @GET("search")
    suspend fun searchPlaces(
        @Query("query") query: String?,
        @Query("near") near: String? = null,
        @Query("ll") latLng: String? = null,
        @Query("limit") limit: Int = 50
    ): PlacesDataDto
}