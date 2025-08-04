package com.aktiia.core.database.mapper

import com.aktiia.core.database.entity.HoursOpenRoom
import com.aktiia.core.database.entity.PhotoRoom
import com.aktiia.core.database.entity.PlaceEntity
import com.aktiia.core.database.entity.SearchEntity
import com.aktiia.core.domain.Photo
import com.aktiia.core.domain.PlaceData
import com.aktiia.core.domain.PlaceDetailsData
import com.aktiia.core.domain.details.HoursOpen


fun SearchEntity.toPlaceData(): PlaceData = PlaceData(
    id = id,
    name = name,
    distance = distance,
    address = address,
    isFavorite = isFavorite
)

fun PlaceData.toPlaceEntity(): SearchEntity = SearchEntity(
    id = id,
    name = name,
    distance = distance,
    address = address,
    isFavorite = isFavorite
)

fun PlaceEntity.toDetailsData(): PlaceDetailsData = PlaceDetailsData(
    id = id,
    name = name,
    description = description,
    tel = tel,
    website = website,
    rating = rating,
    distance = distance,
    hours = hours.toHoursOpenRoom(),
    address = address,
    photos = photos.map { it.toPhoto() },
)

fun PlaceDetailsData.toPlaceEntity(): PlaceEntity = PlaceEntity(
    id = id,
    name = name,
    description = description,
    tel = tel,
    website = website,
    rating = rating,
    distance = distance,
    hours = hours.toHoursOpen(),
    address = address,
    photos = photos.map { it.toPhoto() },
)

fun HoursOpenRoom.toHoursOpenRoom(): HoursOpen = HoursOpen(
    display = display,
    isOpen = isOpen
)
fun HoursOpen.toHoursOpen(): HoursOpenRoom = HoursOpenRoom(
    display = display,
    isOpen = isOpen
)

fun PhotoRoom.toPhoto(): Photo = Photo(
    id = id,
    prefix = prefix,
    suffix = suffix,
    width = width,
    height = height,
)

fun Photo.toPhoto(): PhotoRoom = PhotoRoom(
    id = id,
    prefix = prefix,
    suffix = suffix,
    width = width,
    height = height,
)