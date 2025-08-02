package com.aktiia.core.domain

data class PlaceDetailsData(
    val id: String,
    val name: String,
    val description: String,
    val tel: String,
    val website: String,
    val hours: String,
    val rating: Double,
    val photos: String,
    val distance: String,
    val address: String,
    val isFavorite: Boolean,
)
