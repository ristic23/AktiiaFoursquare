package com.aktiia.foursquare

import android.app.Application
import com.aktiia.features.search.presentation.di.searchViewModelModule
import com.aktiia.foursquare.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class AktiiaApp: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@AktiiaApp)
            modules(
                appModule,
                searchViewModelModule,
            )
        }
    }
}