package com.aktiia.core.database.sources

import android.database.sqlite.SQLiteFullException
import com.aktiia.core.database.dao.DetailsDao
import com.aktiia.core.database.mapper.toDetailsData
import com.aktiia.core.database.mapper.toPlaceEntity
import com.aktiia.core.domain.PlaceDetailsData
import com.aktiia.core.domain.util.DataError
import com.aktiia.core.domain.util.Result
import com.aktiia.features.details.domain.LocaleDetailsDataSource
import com.aktiia.features.details.domain.PlaceId

class RoomDetailsLocaleDataSource(
    private val detailsDao: DetailsDao,
) : LocaleDetailsDataSource {
    override suspend fun upsert(place: PlaceDetailsData): Result<PlaceId, DataError.Local> {
        return try {
            detailsDao.upsert(
                place.toPlaceEntity()
            )
            Result.Success(place.id)
        } catch (e: SQLiteFullException) {
            Result.Error(DataError.Local.DISK_FULL)
        }
    }

    override suspend fun getById(id: String): Result<PlaceDetailsData, DataError.Local> {
        return try {
            val result = detailsDao.getById(id)?.let {
                Result.Success(it.toDetailsData())
            } ?: Result.Error(DataError.Local.ERROR_FETCHING)
            result
        } catch (e: SQLiteFullException) {
            Result.Error(DataError.Local.DISK_FULL)
        }
    }
}