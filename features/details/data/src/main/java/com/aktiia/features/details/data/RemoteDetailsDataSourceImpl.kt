package com.aktiia.features.details.data

import com.aktiia.core.data.network.mapThrowableToNetworkError
import com.aktiia.core.domain.PlaceDetailsData
import com.aktiia.core.domain.util.DataError
import com.aktiia.core.domain.util.Result
import com.aktiia.features.details.data.mapper.toPlaceDetailsData
import com.aktiia.features.details.data.network.DetailsApi
import com.aktiia.features.details.domain.RemoteDetailsDataSource
import kotlinx.coroutines.CancellationException

class RemoteDetailsDataSourceImpl(
    private val detailsApi: DetailsApi
) : RemoteDetailsDataSource {
    override suspend fun fetchDetails(id: String): Result<PlaceDetailsData, DataError.Network> {
        return try {
            val response = detailsApi.getPlaceDetails(id)
            val mapped = response.toPlaceDetailsData()
            Result.Success(mapped)
        } catch (e: Exception) {
            if (e is CancellationException) throw e
            Result.Error(mapThrowableToNetworkError(e))
        }
    }
}