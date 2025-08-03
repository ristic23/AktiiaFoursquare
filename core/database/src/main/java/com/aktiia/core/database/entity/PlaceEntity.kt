package com.aktiia.core.database.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

const val PLACE_TABLE_NAME = "PlaceTable"

@Entity(tableName = PLACE_TABLE_NAME)
data class PlaceEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "fsq_id")
    val id: String,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo(name = "tel")
    val tel: String,
    @ColumnInfo(name = "website")
    val website: String,
    @ColumnInfo(name = "rating")
    val rating: Double,
    @ColumnInfo(name = "address")
    val address: String,
    @ColumnInfo(name = "distance")
    val distance: String,
    @Embedded(prefix = "hours")
    val hours: HoursOpenRoom,
    @ColumnInfo(name = "photos")
    val photos: List<PhotoRoom>,
)

data class HoursOpenRoom(
    @ColumnInfo(name = "display")
    val display: String,
    @ColumnInfo(name = "isOpen")
    val isOpen: Boolean,
)

@Serializable
data class PhotoRoom(
    @ColumnInfo(name = "id")
    val id: String,
    @ColumnInfo(name = "prefix")
    val prefix: String,
    @ColumnInfo(name = "suffix")
    val suffix: String,
    @ColumnInfo(name = "width")
    val width: Int,
    @ColumnInfo(name = "height")
    val height: Int,
)
