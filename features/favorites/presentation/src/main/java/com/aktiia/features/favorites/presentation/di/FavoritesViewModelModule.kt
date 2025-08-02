package com.aktiia.features.favorites.presentation.di

import org.koin.androidx.viewmodel.dsl.viewModel
import com.aktiia.features.favorites.presentation.FavoritesViewModel
import org.koin.dsl.module

val favoriteViewModelModule = module {
    viewModel { FavoritesViewModel(get()) }
}