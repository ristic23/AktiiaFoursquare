package com.aktiia.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.aktiia.core.database.entity.SEARCH_TABLE_NAME
import com.aktiia.core.database.entity.SearchEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchDao {

    @Upsert
    suspend fun upsertAll(results: List<SearchEntity>)

    @Query("SELECT * FROM $SEARCH_TABLE_NAME ORDER BY distance ASC")
    fun getPlaces(): Flow<List<SearchEntity>>

    @Query("UPDATE $SEARCH_TABLE_NAME SET isFavorite = :isFavorite WHERE fsq_id = :id")
    suspend fun updateFavoriteStatus(id: String, isFavorite: Boolean)

    @Query("SELECT * FROM $SEARCH_TABLE_NAME WHERE isFavorite = :isFavorite ORDER BY distance ASC")
    fun getFavoritePlaces(isFavorite: Boolean = true): Flow<List<SearchEntity>>
}