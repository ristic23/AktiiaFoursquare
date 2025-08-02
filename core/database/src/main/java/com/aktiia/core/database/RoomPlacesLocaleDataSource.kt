package com.aktiia.core.database

import android.database.sqlite.SQLiteFullException
import com.aktiia.core.database.dao.PlacesDao
import com.aktiia.core.database.mapper.toPlaceData
import com.aktiia.core.database.mapper.toPlaceEntity
import com.aktiia.core.domain.util.DataError
import com.aktiia.core.domain.util.Result
import com.aktiia.features.search.domain.LocaleSearchDataSource
import com.aktiia.features.search.domain.PlaceData
import com.aktiia.features.search.domain.PlaceId
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoomPlacesLocaleDataSource(
    private val placesDao: PlacesDao
): LocaleSearchDataSource {
    override suspend fun upsertPlaces(places: List<PlaceData>): Result<List<PlaceId>, DataError.Local> {
        return try {
//            placesDao.upsertPlaces(
//                places.map { it.toPlaceEntityPartial() }
//            )
            places.forEach { newPlace ->
                val existing = placesDao.getPlaceById(newPlace.id)
                val placeToInsert = if (existing != null) {
                    newPlace.copy(isFavorite = existing.isFavorite)
                } else {
                    newPlace
                }
                placesDao.insert(placeToInsert.toPlaceEntity())
            }
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

    override suspend fun getFavoritePlaces(isFavorite: Boolean): List<PlaceData> {
        return placesDao.getFavoritePlaces(isFavorite).map { it.toPlaceData() }
    }

}