package com.aktiia.features.search.domain

import com.aktiia.core.domain.util.DataError
import com.aktiia.core.domain.util.EmptyResult
import kotlinx.coroutines.flow.Flow

interface SearchRepository {

    fun getPlaces(): Flow<List<PlaceData>>

    suspend fun search(
        query: String,
        ll: String,
    ): EmptyResult<DataError>
}