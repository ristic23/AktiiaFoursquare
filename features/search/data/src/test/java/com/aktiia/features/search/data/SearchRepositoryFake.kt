package com.aktiia.features.search.data

import com.aktiia.core.domain.PlaceData
import com.aktiia.core.domain.util.DataError
import com.aktiia.core.domain.util.Result
import com.aktiia.features.search.domain.SearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SearchRepositoryFake : SearchRepository {

    var remoteResult: Result<List<PlaceData>, DataError.Network> = Result.Error(DataError.Network.UNKNOWN)
    var localCache: MutableList<PlaceData> = mutableListOf()
    val favorites = mutableSetOf<String>()

    override fun getAllCached(): Flow<List<PlaceData>> = flow {
        emit(localCache)
    }

    override fun search(
        query: String,
        ll: String
    ): Flow<Result<List<PlaceData>, DataError.Network>> = flow {
        when (val result = remoteResult) {
            is Result.Success -> {
                localCache = result.data.toMutableList()
                emit(Result.Success(result.data))
            }

            is Result.Error -> {
                if (localCache.isEmpty()) {
                    emit(result)
                } else {
                    val filtered = localCache.filter {
                        it.name.contains(query, ignoreCase = true)
                    }
                    emit(Result.Success(filtered))
                }
            }
        }
    }

    override suspend fun updateFavoriteStatus(
        id: String,
        isFavorite: Boolean
    ): Result<Boolean, DataError.Local> {
        return try {
            if (isFavorite) favorites.add(id) else favorites.remove(id)
            Result.Success(isFavorite)
        } catch (e: Exception) {
            Result.Error(DataError.Local.DISK_FULL)
        }
    }
}