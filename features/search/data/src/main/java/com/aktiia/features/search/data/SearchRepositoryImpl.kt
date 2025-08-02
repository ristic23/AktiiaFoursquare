package com.aktiia.features.search.data

import com.aktiia.core.domain.util.DataError
import com.aktiia.core.domain.util.EmptyResult
import com.aktiia.features.search.domain.RemoteSearchDataSource
import com.aktiia.features.search.domain.SearchRepository
import com.aktiia.core.domain.util.Result
import com.aktiia.core.domain.util.asEmptyDataResult
import com.aktiia.features.search.domain.PlaceData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SearchRepositoryImpl(
    private val remoteSearchDataSource: RemoteSearchDataSource
): SearchRepository {

    private val _places = MutableStateFlow<List<PlaceData>>(emptyList())
    val places: StateFlow<List<PlaceData>> = _places.asStateFlow()

    override fun getPlaces(): Flow<List<PlaceData>> {
        return places
    }

    override suspend fun search(
        query: String,
        ll: String
    ): EmptyResult<DataError> {
        return when(val result = remoteSearchDataSource.search(query, ll)) {
            is Result.Error -> result.asEmptyDataResult()
            is Result.Success -> {
                // todo write in room
                _places.value = result.data
                result.asEmptyDataResult()
            }
        }
    }

}