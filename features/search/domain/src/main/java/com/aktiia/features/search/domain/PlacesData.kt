package com.aktiia.features.search.domain


data class PlaceData(
    val id: String,
    val name: String,
    val distance: Int?, // in meters
    val timezone: String?,
    val link: String?, // Foursquare URL
//    val categories: List<Category>,
//    val geocodes: Geocodes?,
//    val location: Location?,
//    val relatedPlaces: RelatedPlaces? = null,
//    val chains: List<Chain>? = null
)
