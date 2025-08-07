package com.aktiia.core.database.locale


import com.aktiia.core.database.common.upsert
import com.aktiia.core.domain.PlaceData
import com.aktiia.core.domain.util.DataError
import com.aktiia.core.domain.util.Result
import com.aktiia.features.search.domain.LocaleSearchDataSource
import com.aktiia.features.search.domain.PlaceId
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.collections.map
import kotlin.collections.toList

class LocaleSearchDataSourceFake: LocaleSearchDataSource {

    private var items: MutableList<PlaceData> = mutableListOf<PlaceData>()

    override suspend fun upsertPlaces(places: List<PlaceData>): Result<List<PlaceId>, DataError.Local> {
        items.upsert(places) { old, new -> old.id == new.id }
        return Result.Success(items.map { it.id })
    }

    override fun getPlaces(): Flow<List<PlaceData>> {
        return flow {
            emit(items.toList())
        }
    }
}