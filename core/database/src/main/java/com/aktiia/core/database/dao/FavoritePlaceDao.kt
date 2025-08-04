package com.aktiia.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.aktiia.core.database.entity.FavoritePlaceEntity
import com.aktiia.core.domain.PlaceData
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoritePlaceDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavorite(favorite: FavoritePlaceEntity)

    @Delete
    suspend fun deleteFavorite(favorite: FavoritePlaceEntity)

    @Query("SELECT * FROM FavoriteTable WHERE placeId = :id LIMIT 1")
    suspend fun isFavorite(id: String): FavoritePlaceEntity?

    @Transaction
    @Query("""
    SELECT 
        s.fsq_id AS id,
        s.name AS name,
        s.address AS address,
        s.distance AS distance,
        1 AS isFavorite
    FROM PlacesTable s
    INNER JOIN FavoriteTable f ON s.fsq_id = f.placeId
    """)
    fun getFavoritePlaces(): Flow<List<PlaceData>>
}