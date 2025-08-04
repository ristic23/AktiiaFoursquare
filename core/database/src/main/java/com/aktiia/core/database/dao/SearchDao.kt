package com.aktiia.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.aktiia.core.database.entity.SearchEntity
import com.aktiia.core.domain.PlaceData
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchDao {

    @Upsert
    suspend fun upsertAll(results: List<SearchEntity>)

    @Transaction
    @Query("""
        SELECT 
            s.fsq_id AS id,
            s.name,
            s.address,
            s.distance,
            CASE WHEN f.placeId IS NOT NULL THEN 1 ELSE 0 END AS isFavorite
        FROM PlacesTable s
        LEFT JOIN FavoriteTable f ON s.fsq_id = f.placeId
    """)
    fun getAllPlacesWithFavorite(): Flow<List<PlaceData>>
}