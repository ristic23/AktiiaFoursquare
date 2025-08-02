package com.aktiia.features.search.data.mapper

import com.aktiia.features.search.data.dto.PlaceDataDto
import com.aktiia.features.search.domain.PlaceData

fun PlaceDataDto.toPlacesData(): PlaceData = PlaceData(
    id = id,
    name = name,
    distance = distance?.let { "${it}m" } ?: "",
    address = location.formattedAddress ?: "",
    isFavorite = false,
)