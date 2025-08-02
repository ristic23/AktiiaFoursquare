package com.aktiia.core.database.di

import androidx.room.Room
import com.aktiia.core.database.PlacesDatabase
import com.aktiia.core.database.RoomPlacesLocaleDataSource
import com.aktiia.features.search.domain.LocaleSearchDataSource
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidApplication(),
            PlacesDatabase::class.java,
            "aktiia.places.db"
        ).build()
    }
    single {
        get<PlacesDatabase>().placesDao
    }
    singleOf(::RoomPlacesLocaleDataSource).bind<LocaleSearchDataSource>()
}