package com.aktiia.features.details.data.di

import com.aktiia.features.details.data.DetailsRepositoryImpl
import com.aktiia.features.details.data.RemoteDetailsDataSourceImpl
import com.aktiia.features.details.data.network.DetailsApi
import com.aktiia.features.details.domain.DetailsRepository
import com.aktiia.features.details.domain.RemoteDetailsDataSource
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit

val detailsRepositoryModule = module {
    singleOf(::RemoteDetailsDataSourceImpl).bind<RemoteDetailsDataSource>()
    singleOf(::DetailsRepositoryImpl).bind<DetailsRepository>()

    single<DetailsApi> {
        get<Retrofit>().create(DetailsApi::class.java)
    }
}