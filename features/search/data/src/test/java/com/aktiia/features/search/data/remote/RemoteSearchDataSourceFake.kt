package com.aktiia.features.search.data.remote

import com.aktiia.core.domain.PlaceData
import com.aktiia.core.domain.util.DataError
import com.aktiia.core.domain.util.Result
import com.aktiia.features.search.data.dto.LocationDto
import com.aktiia.features.search.data.dto.PlaceDataDto
import com.aktiia.features.search.domain.RemoteSearchDataSource

class RemoteSearchDataSourceFake: RemoteSearchDataSource {

    var places = (1..10).map {
        PlaceData(
            id = it.toString(),
            name = "Place $it",
            distance = "${100 + it}",
            address = "Address $it",
            isFavorite = false
        )
    }

    override suspend fun search(
        query: String,
        ll: String
    ): Result<List<PlaceData>, DataError.Network> {
        return Result.Success(places)
    }
}