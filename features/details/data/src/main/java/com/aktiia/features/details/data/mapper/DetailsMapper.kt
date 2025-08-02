package com.aktiia.features.details.data.mapper

import com.aktiia.core.domain.Photo
import com.aktiia.core.domain.PlaceDetailsData
import com.aktiia.core.domain.details.HoursOpen
import com.aktiia.features.details.data.dto.HoursOpenDto
import com.aktiia.features.details.data.dto.PhotoDto
import com.aktiia.features.details.data.dto.PlaceDetailsDataDto

fun PlaceDetailsDataDto.toPlaceDetailsData(): PlaceDetailsData = PlaceDetailsData(
    id = id,
    name = name,
    description = description ?: "",
    tel = tel ?: "",
    website = website ?: "",
    rating = rating ?: 0.0,
    distance = distance?.let { "${it}m" } ?: "",
    hours = hours.toHoursOpen(),
    address = address?.formattedAddress ?: "",
    photos = photos.map { it.toPhoto() },
)

fun HoursOpenDto?.toHoursOpen(): HoursOpen = HoursOpen(
    display = this?.display ?: "",
    isOpen = this?.isOpen == true
)

fun PhotoDto.toPhoto(): Photo = Photo(
    id = id,
    prefix = prefix,
    suffix = suffix,
    width = width,
    height = height,
)