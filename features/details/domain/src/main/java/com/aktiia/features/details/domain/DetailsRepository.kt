package com.aktiia.features.details.domain

import com.aktiia.core.domain.PlaceDetailsData
import com.aktiia.core.domain.util.DataError
import com.aktiia.core.domain.util.Result

interface DetailsRepository {
    suspend fun fetchDetails(
        id: String,
    ): Result<PlaceDetailsData, DataError.Network>

//    suspend fun updateFavoriteStatus(
//        id: String,
//        isFavorite: Boolean
//    ): Result<PlaceId, DataError.Local>
}