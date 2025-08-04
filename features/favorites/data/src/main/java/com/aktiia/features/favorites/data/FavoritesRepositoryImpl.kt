package com.aktiia.features.favorites.data

import com.aktiia.core.domain.PlaceData
import com.aktiia.core.domain.favorites.LocaleFavoritesDataSource
import com.aktiia.core.domain.util.DataError
import com.aktiia.core.domain.util.Result
import com.aktiia.features.favorites.domain.FavoritesRepository
import kotlinx.coroutines.flow.Flow

class FavoritesRepositoryImpl(
    private val localeFavoritesDataSource: LocaleFavoritesDataSource,
): FavoritesRepository {

    override suspend fun updateFavoriteStatus(
        id: String,
        isFavorite: Boolean
    ): Result<Boolean, DataError.Local> {
        return try {
            if (isFavorite) {
                localeFavoritesDataSource.addFavoriteStatus(id)
                Result.Success(true)
            } else {
                localeFavoritesDataSource.removeFavoriteStatus(id)
                Result.Success(false)
            }
        } catch (e: Exception) {
            Result.Error(DataError.Local.DISK_FULL)
        }
    }

    override fun getFavoritePlaces(): Flow<List<PlaceData>> {
        return localeFavoritesDataSource.getFavoritePlaces()
    }
}