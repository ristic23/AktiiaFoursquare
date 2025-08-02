package com.aktiia.features.search.data.di

import com.aktiia.features.search.data.RemoteSearchDataSourceImpl
import com.aktiia.features.search.data.SearchRepositoryImpl
import com.aktiia.features.search.domain.RemoteSearchDataSource
import com.aktiia.features.search.domain.SearchRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val searchRepositoryModule = module {
    singleOf(::RemoteSearchDataSourceImpl).bind<RemoteSearchDataSource>()
    singleOf(::SearchRepositoryImpl).bind<SearchRepository>()
}