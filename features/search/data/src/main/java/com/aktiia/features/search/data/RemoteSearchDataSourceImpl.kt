package com.aktiia.features.search.data

import com.aktiia.core.data.network.mapThrowableToNetworkError
import com.aktiia.core.domain.util.DataError
import com.aktiia.core.domain.util.Result
import com.aktiia.features.search.data.mapper.toPlacesData
import com.aktiia.features.search.data.network.SearchApi
import com.aktiia.core.domain.PlaceData
import com.aktiia.features.search.domain.RemoteSearchDataSource
import kotlin.coroutines.cancellation.CancellationException

class RemoteSearchDataSourceImpl(
    private val searchApi: SearchApi,
) : RemoteSearchDataSource {

    override suspend fun search(
        query: String,
        ll: String,
    ): Result<List<PlaceData>, DataError.Network> {
        return try {
            val response = searchApi.searchPlaces(
                query = query,
                latLng = ll,
            )
            val mappedList = response.results.map { it.toPlacesData() }
            Result.Success(mappedList)
        } catch (e: Exception) {
            if (e is CancellationException) throw e
            Result.Error(mapThrowableToNetworkError(e))
        }
    }

}