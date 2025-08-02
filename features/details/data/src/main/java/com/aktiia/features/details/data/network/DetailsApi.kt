package com.aktiia.features.details.data.network

import com.aktiia.features.details.data.dto.PlaceDetailsDataDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DetailsApi {
    @GET("{fsq_id}")
    suspend fun getPlaceDetails(
        @Path(value = "fsq_id", encoded = true) id: String,
        @Query("fields") fields: String = "fsq_id,name,description,tel,website,hours,rating,photos,distance,location"
    ): PlaceDetailsDataDto
}