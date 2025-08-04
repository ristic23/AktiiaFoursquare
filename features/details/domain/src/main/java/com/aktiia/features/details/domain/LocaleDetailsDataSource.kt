package com.aktiia.features.details.domain

import com.aktiia.core.domain.PlaceDetailsData
import com.aktiia.core.domain.util.DataError
import com.aktiia.core.domain.util.Result

typealias PlaceId = String

interface LocaleDetailsDataSource {

    suspend fun upsert(place: PlaceDetailsData): Result<PlaceId, DataError.Local>

    suspend fun getById(id: String): Result<PlaceDetailsData, DataError.Local>
}
