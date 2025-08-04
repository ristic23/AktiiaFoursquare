package com.aktiia.core.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

const val FAVORITE_TABLE_NAME = "FavoriteTable"

@Entity(
    tableName = FAVORITE_TABLE_NAME,
    primaryKeys = ["placeId"],
    foreignKeys = [
        ForeignKey(
            entity = SearchEntity::class,
            parentColumns = ["fsq_id"],
            childColumns = ["placeId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("placeId")]
)
data class FavoritePlaceEntity(
    @ColumnInfo(name = "placeId")
    val placeId: String
)