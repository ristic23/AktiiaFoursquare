package com.aktiia.core.location.di

import com.aktiia.core.location.LocationProviderImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import com.aktiia.core.domain.location.LocationProvider

val locationModule = module {
    singleOf(::LocationProviderImpl).bind<LocationProvider>()
}