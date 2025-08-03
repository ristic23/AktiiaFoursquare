package com.aktiia.core.database.sources

import android.database.sqlite.SQLiteFullException
import com.aktiia.core.database.dao.SearchDao
import com.aktiia.core.database.mapper.toPlaceData
import com.aktiia.core.database.mapper.toPlaceEntity
import com.aktiia.core.domain.util.DataError
import com.aktiia.core.domain.util.Result
import com.aktiia.features.search.domain.LocaleSearchDataSource
import com.aktiia.core.domain.PlaceData
import com.aktiia.features.search.domain.PlaceId
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoomSearchLocaleDataSource(
    private val placesDao: SearchDao
): LocaleSearchDataSource {
    override suspend fun upsertPlaces(places: List<PlaceData>): Result<List<PlaceId>, DataError.Local> {
        return try {
            placesDao.upsertAll(
                places.map { it.toPlaceEntity() }
            )
            Result.Success(places.map { it.id })
        } catch (e: SQLiteFullException) {
            Result.Error(DataError.Local.DISK_FULL)
        }
    }

    override fun getPlaces(): Flow<List<PlaceData>> {
        return placesDao.getPlaces()
            .map { placesEntities ->
                placesEntities.map { it.toPlaceData() }
            }
    }

    override suspend fun updateFavoriteStatus(
        id: String,
        isFavorite: Boolean
    ): Result<PlaceId, DataError.Local> {
        return try {
            placesDao.updateFavoriteStatus(id, isFavorite)
            Result.Success(id)
        } catch (e: SQLiteFullException) {
            Result.Error(DataError.Local.DISK_FULL)
        }
    }
}