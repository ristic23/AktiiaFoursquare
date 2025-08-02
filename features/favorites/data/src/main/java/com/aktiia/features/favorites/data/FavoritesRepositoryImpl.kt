package com.aktiia.features.favorites.data

import com.aktiia.core.domain.PlaceData
import com.aktiia.core.domain.util.DataError
import com.aktiia.core.domain.util.Result
import com.aktiia.features.favorites.domain.FavoritesRepository
import com.aktiia.features.favorites.domain.LocaleFavoritesDataSource
import com.aktiia.features.favorites.domain.PlaceId
import kotlinx.coroutines.flow.Flow

class FavoritesRepositoryImpl(
    private val localeFavoritesDataSource: LocaleFavoritesDataSource
): FavoritesRepository {
    override suspend fun updateFavoriteStatus(id: String): Result<PlaceId, DataError.Local> {
        return localeFavoritesDataSource.removeFavoriteStatus(id)
    }

    override fun getFavoritePlaces(): Flow<List<PlaceData>> {
        return localeFavoritesDataSource.getFavoritePlaces()
    }
}