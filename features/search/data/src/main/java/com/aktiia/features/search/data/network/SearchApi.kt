package com.aktiia.features.search.data.network

import com.aktiia.features.search.data.dto.PlacesDataDto
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApi {
    @GET("search")
    suspend fun searchPlaces(
        @Query("query") query: String?,
        @Query("ll") latLng: String? = null,
        @Query("limit") limit: Int = 50,
        @Query("fields") fields: String = "fsq_id,name,location,distance"
    ): PlacesDataDto
}