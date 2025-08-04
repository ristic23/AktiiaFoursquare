package com.aktiia.features.search.domain

import com.aktiia.core.domain.PlaceData
import com.aktiia.core.domain.util.DataError
import com.aktiia.core.domain.util.Result
import kotlinx.coroutines.flow.Flow

interface SearchRepository {

    fun getAllCached(): Flow<List<PlaceData>>

    fun search(
        query: String,
        ll: String,
    ): Flow<Result<List<PlaceData>, DataError.Network>>

    suspend fun updateFavoriteStatus(id: String, isFavorite: Boolean): Result<Boolean, DataError.Local>
}