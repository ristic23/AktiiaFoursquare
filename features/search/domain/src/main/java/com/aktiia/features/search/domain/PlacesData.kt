package com.aktiia.features.search.domain


data class PlaceData(
    val id: String,
    val name: String,
    val distance: Int?, // in meters
    val location: Location,
)

data class Location(
    val formattedAddress: String?,
)
