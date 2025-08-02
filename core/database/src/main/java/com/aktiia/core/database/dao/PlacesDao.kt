package com.aktiia.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.aktiia.core.database.entity.PlaceEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PlacesDao {

    @Upsert
    suspend fun upsertPlaces(placesEntity: List<PlaceEntity>)

    @Query("SELECT * FROM placeentity ORDER BY distance ASC")
    fun getPlaces(): Flow<List<PlaceEntity>>
}