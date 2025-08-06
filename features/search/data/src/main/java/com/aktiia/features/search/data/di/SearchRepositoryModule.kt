package com.aktiia.features.search.data.di

import com.aktiia.features.search.data.RemoteSearchDataSourceImpl
import com.aktiia.features.search.data.SearchRepositoryImpl
import com.aktiia.features.search.data.network.SearchApi
import com.aktiia.features.search.domain.RemoteSearchDataSource
import com.aktiia.features.search.domain.SearchRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit

val searchRepositoryModule = module {
    singleOf(::RemoteSearchDataSourceImpl).bind<RemoteSearchDataSource>()
    singleOf(::SearchRepositoryImpl).bind<SearchRepository>()

    single<SearchApi> {
        get<Retrofit>().create(SearchApi::class.java)
    }
}