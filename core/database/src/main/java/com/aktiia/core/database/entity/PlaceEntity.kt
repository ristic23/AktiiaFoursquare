package com.aktiia.core.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PlaceEntity (
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "fsq_id")
    val id: String,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "formattedAddress")
    val formattedAddress: String?,
    @ColumnInfo(name = "distance")
    val distance: Int?,
    @ColumnInfo(name = "isFavorite")
    val isFavorite: Boolean = false,
)