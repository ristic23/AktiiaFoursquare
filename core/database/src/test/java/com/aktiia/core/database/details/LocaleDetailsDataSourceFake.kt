package com.aktiia.core.database.details

import com.aktiia.core.database.common.upsert
import com.aktiia.core.domain.PlaceDetailsData
import com.aktiia.core.domain.util.DataError
import com.aktiia.core.domain.util.Result
import com.aktiia.features.details.domain.LocaleDetailsDataSource
import com.aktiia.features.details.domain.PlaceId

class LocaleDetailsDataSourceFake : LocaleDetailsDataSource {

    private var items: MutableList<PlaceDetailsData> = mutableListOf<PlaceDetailsData>()

    override suspend fun upsert(place: PlaceDetailsData): Result<PlaceId, DataError.Local> {
        items.upsert(place) { old, new -> old.id == new.id }
        return Result.Success(place.id)
    }

    override suspend fun getById(id: String): Result<PlaceDetailsData, DataError.Local> {
        return items.firstOrNull { it.id == id } ?.let {
            Result.Success(it)
        } ?: Result.Error(DataError.Local.ERROR_FETCHING)
    }
}
