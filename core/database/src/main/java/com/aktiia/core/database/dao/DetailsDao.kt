package com.aktiia.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.aktiia.core.database.entity.PlaceEntity

@Dao
interface DetailsDao {

    @Upsert
    suspend fun upsert(place: PlaceEntity)

    @Query("SELECT * FROM PlaceTable WHERE fsq_id = :id LIMIT 1")
    suspend fun getById(id: String): PlaceEntity?
}