package com.aktiia.foursquare.di

import com.aktiia.foursquare.AktiiaApp
import kotlinx.coroutines.CoroutineScope
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val appModule = module {
    single<CoroutineScope> {
        (androidApplication() as AktiiaApp).applicationScope
    }
}