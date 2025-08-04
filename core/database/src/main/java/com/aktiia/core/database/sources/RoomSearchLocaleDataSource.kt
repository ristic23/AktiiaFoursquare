package com.aktiia.core.database.sources

import android.database.sqlite.SQLiteFullException
import com.aktiia.core.database.dao.SearchDao
import com.aktiia.core.database.mapper.toPlaceEntity
import com.aktiia.core.domain.PlaceData
import com.aktiia.core.domain.util.DataError
import com.aktiia.core.domain.util.Result
import com.aktiia.features.search.domain.LocaleSearchDataSource
import com.aktiia.features.search.domain.PlaceId
import kotlinx.coroutines.flow.Flow

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
        return placesDao.getAllPlacesWithFavorite()
    }
}