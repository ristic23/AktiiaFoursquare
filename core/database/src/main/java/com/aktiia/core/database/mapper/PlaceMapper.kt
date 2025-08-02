package com.aktiia.core.database.mapper

import com.aktiia.core.database.entity.PlaceEntity
import com.aktiia.core.domain.PlaceData


fun PlaceEntity.toPlaceData(): PlaceData = PlaceData(
    id = id,
    name = name,
    distance = distance,
    address = formattedAddress,
    isFavorite = isFavorite
)

fun PlaceData.toPlaceEntity(): PlaceEntity = PlaceEntity(
    id = id,
    name = name,
    distance = distance,
    formattedAddress = address,
    isFavorite = isFavorite
)
