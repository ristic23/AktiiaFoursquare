package com.aktiia.features.search.domain

import com.aktiia.core.domain.PlaceData
import com.aktiia.core.domain.util.DataError
import com.aktiia.core.domain.util.Result
import kotlinx.coroutines.flow.Flow

typealias PlaceId = String

interface LocaleSearchDataSource {

    suspend fun upsertPlaces(places: List<PlaceData>): Result<List<PlaceId>, DataError.Local>

    fun getPlaces(): Flow<List<PlaceData>>
}