package com.aktiia.features.search.presentation.di

import com.aktiia.features.search.presentation.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val searchViewModelModule = module {
    viewModel { SearchViewModel(get(), get()) }
}