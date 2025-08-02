package com.aktiia.core.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

const val PLACE_TABLE_NAME = "PlacesTable"

@Entity(tableName = PLACE_TABLE_NAME)
data class PlaceEntity (
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "fsq_id")
    val id: String,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "formattedAddress")
    val formattedAddress: String,
    @ColumnInfo(name = "distance")
    val distance: String,
    @ColumnInfo(name = "isFavorite")
    val isFavorite: Boolean = false,
)
