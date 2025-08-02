package com.aktiia.features.details.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlaceDetailsDataDto(
    @SerialName("fsq_id") val id: String,
    @SerialName("name") val name: String,
    @SerialName("description") val description: String? = null,
    @SerialName("distance") val distance: Int? = null,
    @SerialName("tel") val tel: String? = null,
    @SerialName("website") val website: String? = null,
    @SerialName("rating") val rating: Double? = null,
    @SerialName("location") val address: LocationDto? = null,
    @SerialName("hours") val hours: HoursOpenDto? = null,
    @SerialName("photos") val photos: List<PhotoDto> = emptyList(),
)

@Serializable
data class LocationDto(
    @SerialName("formatted_address") val formattedAddress: String? = null,
)

@Serializable
data class HoursOpenDto(
    @SerialName("display") val display: String? = null,
    @SerialName("open_now") val isOpen: Boolean? = null,
)

@Serializable
data class PhotoDto(
    @SerialName("id") val id: String,
    @SerialName("prefix") val prefix: String,
    @SerialName("suffix") val suffix: String,
    @SerialName("width") val width: Int,
    @SerialName("height") val height: Int,
)


