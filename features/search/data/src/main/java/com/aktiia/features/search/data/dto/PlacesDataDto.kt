package com.aktiia.features.search.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlacesDataDto(
    @SerialName("results")
    val results: List<PlaceDataDto>
)

@Serializable
data class PlaceDataDto(
    @SerialName("fsq_id") val id: String,
    @SerialName("name") val name: String,
    @SerialName("location") val location: LocationDto,
    @SerialName("distance") val distance: Int?, // in meters
)

@Serializable
data class LocationDto(
    @SerialName("formatted_address") val formattedAddress: String?,
)