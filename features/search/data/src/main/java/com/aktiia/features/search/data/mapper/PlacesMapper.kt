package com.aktiia.features.search.data.mapper

import com.aktiia.features.search.data.dto.LocationDto
import com.aktiia.features.search.data.dto.PlaceDataDto
import com.aktiia.features.search.domain.Location
import com.aktiia.features.search.domain.PlaceData

fun PlaceDataDto.toPlacesData(): PlaceData = PlaceData(
    id = id,
    name = name,
    distance = distance,
    location = location.toLocation(),
)

fun LocationDto.toLocation(): Location = Location(
    formattedAddress = formattedAddress
)