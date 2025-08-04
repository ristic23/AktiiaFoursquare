package com.aktiia.features.favorites.domain

import com.aktiia.core.domain.PlaceData
import com.aktiia.core.domain.util.DataError
import com.aktiia.core.domain.util.Result
import kotlinx.coroutines.flow.Flow

interface FavoritesRepository {

    suspend fun updateFavoriteStatus(id: String, isFavorite: Boolean): Result<Boolean, DataError.Local>

    fun getFavoritePlaces(): Flow<List<PlaceData>>
}