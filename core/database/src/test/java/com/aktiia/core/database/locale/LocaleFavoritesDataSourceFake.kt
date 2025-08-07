package com.aktiia.core.database.locale

import com.aktiia.core.domain.PlaceData
import com.aktiia.core.domain.favorites.LocaleFavoritesDataSource
import com.aktiia.core.domain.util.DataError
import com.aktiia.core.domain.util.EmptyResult
import com.aktiia.core.domain.util.Result
import com.aktiia.core.domain.util.asEmptyDataResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.collections.filter

class LocaleFavoritesDataSourceFake(
    private val roomSearchLocaleDataSourceFake: LocaleSearchDataSourceFake
): LocaleFavoritesDataSource {

    private var items: MutableList<String> = mutableListOf<String>()

    override suspend fun removeFavoriteStatus(id: String): EmptyResult<DataError.Local> {
        return Result.Success(items.remove(id)).asEmptyDataResult()
    }

    override suspend fun addFavoriteStatus(id: String): EmptyResult<DataError.Local> {
        return Result.Success(items.add(id)).asEmptyDataResult()
    }

    override suspend fun isFavorite(id: String): Result<Boolean, DataError.Local> {
        return Result.Success(items.contains(id))
    }

    override fun getFavoritePlaces(): Flow<List<PlaceData>> {
        return roomSearchLocaleDataSourceFake.getPlaces()
            .map { allPlaces -> allPlaces.filter { it.id in items } }
    }
}