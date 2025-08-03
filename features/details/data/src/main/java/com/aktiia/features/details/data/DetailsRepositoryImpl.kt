package com.aktiia.features.details.data

import com.aktiia.core.domain.PlaceDetailsData
import com.aktiia.core.domain.util.DataError
import com.aktiia.core.domain.util.Result
import com.aktiia.features.details.domain.DetailsRepository
import com.aktiia.features.details.domain.LocaleDetailsDataSource
import com.aktiia.features.details.domain.RemoteDetailsDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class DetailsRepositoryImpl(
    private val remoteDetailsDataSource: RemoteDetailsDataSource,
    private val localeDetailsDataSource: LocaleDetailsDataSource,
) : DetailsRepository {

    override fun fetchDetails(
        id: String
    ): Flow<Result<PlaceDetailsData, DataError.Network>> = flow {
        val localResult = localeDetailsDataSource.getById(id)
        val hasCached = localResult is Result.Success
        (localResult as? Result.Success)?.data?.let {
            emit(Result.Success(it))
        }

        when (val resultApi = remoteDetailsDataSource.fetchDetails(id)) {
            is Result.Error -> {
                if (!hasCached) emit(resultApi)
            }

            is Result.Success -> {
                localeDetailsDataSource.upsert(resultApi.data)
                emit(Result.Success(resultApi.data))
            }
        }
    }.catch { emit(Result.Error(DataError.Network.UNKNOWN)) }
        .flowOn(Dispatchers.IO)
        .distinctUntilChanged { old, new ->
            old is Result.Success && new is Result.Success && old.data == new.data
        }

//    override suspend fun updateFavoriteStatus(
//        id: String,
//        isFavorite: Boolean
//    ): Result<PlaceId, DataError.Local> {
//        // todo with database
//    }
}