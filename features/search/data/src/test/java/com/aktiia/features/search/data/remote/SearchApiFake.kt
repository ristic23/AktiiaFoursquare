package com.aktiia.features.search.data.remote

import com.aktiia.features.search.data.dto.LocationDto
import com.aktiia.features.search.data.dto.PlaceDataDto
import com.aktiia.features.search.data.dto.PlacesDataDto
import com.aktiia.features.search.data.network.SearchApi

class SearchApiFake: SearchApi {

    var places = (1..10).map {
        PlaceDataDto(
            id = it.toString(),
            name = "Place $it",
            distance = 100 + it,
            location = LocationDto("Address $it"),
        )
    }

    override suspend fun searchPlaces(
        query: String?,
        latLng: String?,
        limit: Int,
        fields: String
    ): PlacesDataDto {
        return PlacesDataDto(places)
    }

}