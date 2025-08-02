package com.aktiia.features.search.domain


data class PlaceData(
    val id: String,
    val name: String,
    val distance: String,
    val address: String,
    val isFavorite: Boolean,
)

