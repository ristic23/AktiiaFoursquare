package com.aktiia.features.details.data

import com.aktiia.core.domain.PlaceDetailsData
import com.aktiia.core.domain.util.DataError
import com.aktiia.core.domain.util.Result
import com.aktiia.features.details.domain.DetailsRepository
import com.aktiia.features.details.domain.LocaleDetailsDataSource
import com.aktiia.features.details.domain.PlaceId
import com.aktiia.features.details.domain.RemoteDetailsDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async

class DetailsRepositoryImpl(
    private val remoteDetailsDataSource: RemoteDetailsDataSource,
//    private val localeDetailsDataSource: LocaleDetailsDataSource,
    private val applicationScope: CoroutineScope,
) : DetailsRepository {
    override suspend fun fetchDetails(
        id: String
    ): Result<PlaceDetailsData, DataError.Network> {
        return when (val result = remoteDetailsDataSource.fetchDetails(id)) {
            is Result.Error -> result
            is Result.Success -> {
                applicationScope.async {
                    // todo write in room
                }.await()
                result
            }
        }
    }

//    override suspend fun updateFavoriteStatus(
//        id: String,
//        isFavorite: Boolean
//    ): Result<PlaceId, DataError.Local> {
//        // todo with database
//    }
}