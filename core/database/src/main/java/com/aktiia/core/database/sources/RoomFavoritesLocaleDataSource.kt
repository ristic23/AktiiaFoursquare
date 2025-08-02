package com.aktiia.core.database.sources

import android.database.sqlite.SQLiteFullException
import com.aktiia.core.database.dao.PlacesDao
import com.aktiia.core.database.mapper.toPlaceData
import com.aktiia.core.domain.PlaceData
import com.aktiia.core.domain.util.DataError
import com.aktiia.core.domain.util.Result
import com.aktiia.features.favorites.domain.LocaleFavoritesDataSource
import com.aktiia.features.favorites.domain.PlaceId
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoomFavoritesLocaleDataSource(
    private val placesDao: PlacesDao
): LocaleFavoritesDataSource {
    override suspend fun removeFavoriteStatus(id: String): Result<PlaceId, DataError.Local> {
        return try {
            placesDao.updateFavoriteStatus(id, false)
            Result.Success(id)
        } catch (e: SQLiteFullException) {
            Result.Error(DataError.Local.DISK_FULL)
        }
    }

    override fun getFavoritePlaces(): Flow<List<PlaceData>> {
        return placesDao.getFavoritePlaces().map { favorites ->
            favorites.map { entity -> entity.toPlaceData() }
        }
    }
}