package com.aktiia.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.aktiia.core.database.entity.PLACE_TABLE_NAME
import com.aktiia.core.database.entity.PlaceEntity

@Dao
interface DetailsDao {

    @Upsert
    suspend fun upsert(place: PlaceEntity)

    @Query("SELECT * FROM $PLACE_TABLE_NAME WHERE fsq_id = :id LIMIT 1")
    suspend fun getById(id: String): PlaceEntity?

//    suspend fun updateFavorites()
}