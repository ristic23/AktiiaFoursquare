package com.aktiia.features.favorites.data.di

import com.aktiia.features.favorites.data.FavoritesRepositoryImpl
import com.aktiia.features.favorites.domain.FavoritesRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val favoritesRepositoryModule = module {
    singleOf(::FavoritesRepositoryImpl).bind<FavoritesRepository>()
}
