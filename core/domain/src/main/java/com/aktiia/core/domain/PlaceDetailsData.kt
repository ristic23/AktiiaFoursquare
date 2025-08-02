package com.aktiia.core.domain

import com.aktiia.core.domain.details.HoursOpen

data class PlaceDetailsData(
    val id: String,
    val name: String,
    val description: String,
    val tel: String,
    val website: String,
    val rating: Double,
    val distance: String,
    val address: String,
    val isFavorite: Boolean = false,
    val hours: HoursOpen,
    val photos: List<Photo>,
)
