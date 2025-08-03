package com.aktiia.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.aktiia.core.database.converter.TypeConverts
import com.aktiia.core.database.dao.DetailsDao
import com.aktiia.core.database.dao.SearchDao
import com.aktiia.core.database.entity.PlaceEntity
import com.aktiia.core.database.entity.SearchEntity

@Database(
    entities = [
        SearchEntity::class,
        PlaceEntity::class
    ],
    version = 1
)
@TypeConverters(TypeConverts::class)
abstract class PlacesDatabase : RoomDatabase() {

    abstract val searchDao: SearchDao
    abstract val detailsDao: DetailsDao
}