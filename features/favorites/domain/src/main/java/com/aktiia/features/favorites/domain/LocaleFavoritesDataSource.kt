package com.aktiia.features.favorites.domain

import com.aktiia.core.domain.PlaceData
import com.aktiia.core.domain.util.DataError
import com.aktiia.core.domain.util.Result
import kotlinx.coroutines.flow.Flow

typealias PlaceId = String

interface LocaleFavoritesDataSource {

    suspend fun removeFavoriteStatus(id: String): Result<PlaceId, DataError.Local>

    fun getFavoritePlaces(): Flow<List<PlaceData>>
}