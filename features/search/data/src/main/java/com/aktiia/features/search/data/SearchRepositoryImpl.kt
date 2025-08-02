package com.aktiia.features.search.data

import com.aktiia.core.domain.util.DataError
import com.aktiia.core.domain.util.EmptyResult
import com.aktiia.core.domain.util.Result
import com.aktiia.core.domain.util.asEmptyDataResult
import com.aktiia.features.search.domain.LocaleSearchDataSource
import com.aktiia.features.search.domain.PlaceData
import com.aktiia.features.search.domain.PlaceId
import com.aktiia.features.search.domain.RemoteSearchDataSource
import com.aktiia.features.search.domain.SearchRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow

class SearchRepositoryImpl(
    private val remoteSearchDataSource: RemoteSearchDataSource,
    private val localeSearchDataSource: LocaleSearchDataSource,
    private val applicationScope: CoroutineScope,
) : SearchRepository {

    override fun getPlaces(): Flow<List<PlaceData>> {
        return localeSearchDataSource.getPlaces()
    }

    override suspend fun search(
        query: String,
        ll: String
    ): EmptyResult<DataError> {
        return when (val result = remoteSearchDataSource.search(query, ll)) {
            is Result.Error -> result.asEmptyDataResult()
            is Result.Success -> {
                applicationScope.async {
                    localeSearchDataSource.upsertPlaces(result.data).asEmptyDataResult()
                }.await()
            }
        }
    }

    override suspend fun updateFavoriteStatus(
        id: String,
        isFavorite: Boolean
    ): Result<PlaceId, DataError.Local> {
        return localeSearchDataSource.updateFavoriteStatus(id, isFavorite)
    }

    override suspend fun getFavoritePlaces(isFavorite: Boolean): List<PlaceData> {
        return localeSearchDataSource.getFavoritePlaces(isFavorite)
    }

}