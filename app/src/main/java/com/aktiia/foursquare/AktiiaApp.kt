package com.aktiia.foursquare

import android.app.Application
import com.aktiia.core.database.di.databaseModule
import com.aktiia.features.favorites.data.di.favoritesRepositoryModule
import com.aktiia.features.favorites.presentation.di.favoriteViewModelModule
import com.aktiia.features.search.data.di.searchRepositoryModule
import com.aktiia.features.search.presentation.di.searchViewModelModule
import com.aktiia.foursquare.di.appModule
import com.aktiia.foursquare.di.networkModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class AktiiaApp: Application() {

    val applicationScope = CoroutineScope(SupervisorJob())

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@AktiiaApp)
            modules(
                appModule,
                searchViewModelModule,
                networkModule,
                searchRepositoryModule,
                databaseModule,
                favoriteViewModelModule,
                favoritesRepositoryModule,
            )
        }
    }
}