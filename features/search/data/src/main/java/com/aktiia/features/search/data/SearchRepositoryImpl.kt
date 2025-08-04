package com.aktiia.features.search.data

import com.aktiia.core.domain.PlaceData
import com.aktiia.core.domain.favorites.LocaleFavoritesDataSource
import com.aktiia.core.domain.util.DataError
import com.aktiia.core.domain.util.Result
import com.aktiia.features.search.domain.LocaleSearchDataSource
import com.aktiia.features.search.domain.RemoteSearchDataSource
import com.aktiia.features.search.domain.SearchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class SearchRepositoryImpl(
    private val remoteSearchDataSource: RemoteSearchDataSource,
    private val localeSearchDataSource: LocaleSearchDataSource,
    private val localeFavoritesDataSource: LocaleFavoritesDataSource
) : SearchRepository {

    override fun getAllCached(): Flow<List<PlaceData>> {
        return localeSearchDataSource.getPlaces()
    }

    override fun search(
        query: String,
        ll: String
    ): Flow<Result<List<PlaceData>, DataError.Network>> = flow {
        when (val api = remoteSearchDataSource.search(query, ll)) {
            is Result.Success -> {
                localeSearchDataSource.upsertPlaces(api.data)
                emit(Result.Success(api.data))
            }

            is Result.Error -> {
                val currentCache = localeSearchDataSource.getPlaces().firstOrNull().orEmpty()
                if (currentCache.isEmpty()) {
                    emit(api)
                } else {
                    val filtered = currentCache.filter { place ->
                        place.name.contains(query, ignoreCase = true)
                    }
                    emit(Result.Success(filtered))
                }
            }
        }
    }.catch { emit(Result.Error(DataError.Network.UNKNOWN)) }
        .flowOn(Dispatchers.IO)
        .distinctUntilChanged { old, new ->
            old is Result.Success && new is Result.Success && old.data == new.data
        }

    override suspend fun updateFavoriteStatus(
        id: String,
        isFavorite: Boolean
    ): Result<Boolean, DataError.Local> {
        return try {
            if (isFavorite) {
                localeFavoritesDataSource.addFavoriteStatus(id)
                Result.Success(true)
            } else {
                localeFavoritesDataSource.removeFavoriteStatus(id)
                Result.Success(false)
            }
        } catch (e: Exception) {
            Result.Error(DataError.Local.DISK_FULL)
        }
    }
}