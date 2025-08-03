package com.aktiia.core.database.mapper

import com.aktiia.core.database.entity.SearchEntity
import com.aktiia.core.domain.PlaceData


fun SearchEntity.toPlaceData(): PlaceData = PlaceData(
    id = id,
    name = name,
    distance = distance,
    address = formattedAddress,
    isFavorite = isFavorite
)

fun PlaceData.toPlaceEntity(): SearchEntity = SearchEntity(
    id = id,
    name = name,
    distance = distance,
    formattedAddress = address,
    isFavorite = isFavorite
)
