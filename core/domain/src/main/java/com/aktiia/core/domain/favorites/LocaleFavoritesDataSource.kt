package com.aktiia.core.domain.favorites

import com.aktiia.core.domain.PlaceData
import com.aktiia.core.domain.util.DataError
import com.aktiia.core.domain.util.EmptyResult
import com.aktiia.core.domain.util.Result
import kotlinx.coroutines.flow.Flow

interface LocaleFavoritesDataSource {

    suspend fun removeFavoriteStatus(id: String): EmptyResult<DataError.Local>

    suspend fun addFavoriteStatus(id: String): EmptyResult<DataError.Local>

    suspend fun isFavorite(id: String): Result<Boolean, DataError.Local>

    fun getFavoritePlaces(): Flow<List<PlaceData>>
}