package com.aktiia.core.database.di

import androidx.room.Room
import com.aktiia.core.database.PlacesDatabase
import com.aktiia.core.database.converter.TypeConverts
import com.aktiia.core.database.sources.RoomDetailsLocaleDataSource
import com.aktiia.core.database.sources.RoomFavoritesLocaleDataSource
import com.aktiia.core.database.sources.RoomSearchLocaleDataSource
import com.aktiia.core.domain.favorites.LocaleFavoritesDataSource
import com.aktiia.features.details.domain.LocaleDetailsDataSource
import com.aktiia.features.search.domain.LocaleSearchDataSource
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val databaseModule = module {
    single { TypeConverts() }
    single {
        Room
            .databaseBuilder(
            androidApplication(),
            PlacesDatabase::class.java,
            "aktiia.places.db")
            .addTypeConverter(get<TypeConverts>())
            .build()
    }
    single { get<PlacesDatabase>().searchDao }
    single { get<PlacesDatabase>().detailsDao }
    single { get<PlacesDatabase>().favoritePlaceDao }

    singleOf(::RoomSearchLocaleDataSource).bind<LocaleSearchDataSource>()
    singleOf(::RoomFavoritesLocaleDataSource).bind<LocaleFavoritesDataSource>()
    singleOf(::RoomDetailsLocaleDataSource).bind<LocaleDetailsDataSource>()
}