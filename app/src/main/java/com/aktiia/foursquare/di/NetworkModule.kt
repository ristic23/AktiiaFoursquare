package com.aktiia.foursquare.di

import com.aktiia.core.presentation.designsystem.BuildConfig
import com.aktiia.features.search.data.network.SearchApi
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import okhttp3.MediaType.Companion.toMediaType

val networkModule = module {
    single {
        Json {
            ignoreUnknownKeys = true
            prettyPrint = false
            isLenient = true
        }
    }

    single {
        OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("Authorization", BuildConfig.API_KEY)
                    .build()
                chain.proceed(request)
            }
            .build()
    }

    single {
        val contentType = "application/json".toMediaType()

        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(get())
            .addConverterFactory(get<Json>().asConverterFactory(contentType))
            .build()
    }

    single<SearchApi> {
        get<Retrofit>().create(SearchApi::class.java)
    }
}