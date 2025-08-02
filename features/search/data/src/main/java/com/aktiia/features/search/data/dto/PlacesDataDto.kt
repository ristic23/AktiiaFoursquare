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
    @SerialName("distance") val distance: Int?, // in meters
    @SerialName("timezone") val timezone: String?,
    @SerialName("link") val link: String?, // Foursquare URL
//    @SerialName("categories") val categories: List<Category>,
//    @SerialName("geocodes") val geocodes: Geocodes?,
//    @SerialName("location") val location: Location?,
//    @SerialName("related_places") val relatedPlaces: RelatedPlaces? = null,
//    @SerialName("chains") val chains: List<Chain>? = null
)