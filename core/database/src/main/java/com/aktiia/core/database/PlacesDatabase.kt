package com.aktiia.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.aktiia.core.database.dao.PlacesDao
import com.aktiia.core.database.entity.PlaceEntity

@Database(
    entities = [PlaceEntity::class],
    version = 1
)
abstract class PlacesDatabase: RoomDatabase() {

    abstract val placesDao: PlacesDao
}