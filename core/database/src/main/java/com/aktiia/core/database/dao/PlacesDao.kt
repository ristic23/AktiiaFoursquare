package com.aktiia.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aktiia.core.database.entity.PLACE_TABLE_NAME
import com.aktiia.core.database.entity.PlaceEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PlacesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(place: PlaceEntity)

    @Query("SELECT * FROM $PLACE_TABLE_NAME WHERE fsq_id = :id LIMIT 1")
    suspend fun getPlaceById(id: String): PlaceEntity?

    @Query("SELECT * FROM $PLACE_TABLE_NAME ORDER BY distance ASC")
    fun getPlaces(): Flow<List<PlaceEntity>>

    @Query("UPDATE $PLACE_TABLE_NAME SET isFavorite = :isFavorite WHERE fsq_id = :id")
    suspend fun updateFavoriteStatus(id: String, isFavorite: Boolean)

    @Query("SELECT * FROM $PLACE_TABLE_NAME WHERE isFavorite = :isFavorite ORDER BY distance ASC")
    fun getFavoritePlaces(isFavorite: Boolean = true): Flow<List<PlaceEntity>>
}