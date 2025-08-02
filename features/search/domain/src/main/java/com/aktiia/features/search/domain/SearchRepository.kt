package com.aktiia.features.search.domain

import com.aktiia.core.domain.util.DataError
import com.aktiia.core.domain.util.EmptyResult

interface SearchRepository {

    suspend fun search(
        query: String,
        ll: String,
    ): EmptyResult<DataError>
}