package com.aktiia.core.database.sources

import android.database.sqlite.SQLiteFullException
import com.aktiia.core.database.dao.FavoritePlaceDao
import com.aktiia.core.database.entity.FavoritePlaceEntity
import com.aktiia.core.domain.PlaceData
import com.aktiia.core.domain.util.DataError
import com.aktiia.core.domain.util.Result
import com.aktiia.core.domain.favorites.LocaleFavoritesDataSource
import com.aktiia.core.domain.util.EmptyResult
import com.aktiia.core.domain.util.asEmptyDataResult
import kotlinx.coroutines.flow.Flow

class RoomFavoritesLocaleDataSource(
    private val favoritePlaceDao: FavoritePlaceDao
): LocaleFavoritesDataSource {
    override suspend fun removeFavoriteStatus(id: String): EmptyResult<DataError.Local> {
        return try {
            favoritePlaceDao.deleteFavorite(FavoritePlaceEntity(id))
            Result.Success(true).asEmptyDataResult()
        } catch (e: SQLiteFullException) {
            Result.Error(DataError.Local.DISK_FULL)
        }
    }

    override suspend fun addFavoriteStatus(id: String): EmptyResult<DataError.Local> {
        return try {
            favoritePlaceDao.insertFavorite(FavoritePlaceEntity(id))
            Result.Success(true).asEmptyDataResult()
        } catch (e: SQLiteFullException) {
            Result.Error(DataError.Local.DISK_FULL)
        }
    }

    override suspend fun isFavorite(id: String): Result<Boolean, DataError.Local> {
        return try {
            Result.Success(favoritePlaceDao.isFavorite(id) != null)
        } catch (e: SQLiteFullException) {
            Result.Error(DataError.Local.DISK_FULL)
        }
    }

    override fun getFavoritePlaces(): Flow<List<PlaceData>> {
        return favoritePlaceDao.getFavoritePlaces()
    }
}