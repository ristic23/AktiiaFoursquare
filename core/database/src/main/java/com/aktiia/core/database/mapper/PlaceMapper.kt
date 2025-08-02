package com.aktiia.core.database.mapper

import com.aktiia.core.database.entity.PlaceEntity
import com.aktiia.features.search.domain.Location
import com.aktiia.features.search.domain.PlaceData


fun PlaceEntity.toPlaceData(): PlaceData = PlaceData(
    id = id,
    name = name,
    distance = distance,
    location = Location(formattedAddress = formattedAddress),
)

fun PlaceData.toPlaceEntity(): PlaceEntity = PlaceEntity(
    id = id,
    name = name,
    distance = distance,
    formattedAddress = location.formattedAddress,
)
