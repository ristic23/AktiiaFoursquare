package com.aktiia.features.search.domain

import com.aktiia.core.domain.util.DataError
import com.aktiia.core.domain.util.Result


interface RemoteSearchDataSource {

    suspend fun search(
        query: String,
        ll: String,
    ): Result<List<PlaceData>, DataError.Network>
}
