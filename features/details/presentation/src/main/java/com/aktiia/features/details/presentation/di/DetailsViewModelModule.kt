package com.aktiia.features.details.presentation.di

import com.aktiia.features.details.presentation.DetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val detailsViewModelModule = module {
    viewModel { DetailsViewModel(get()) }
}
