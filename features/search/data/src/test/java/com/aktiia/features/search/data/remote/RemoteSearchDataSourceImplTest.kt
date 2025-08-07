package com.aktiia.features.search.data.remote

import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import kotlin.test.assertTrue
import com.aktiia.core.domain.util.Result
import com.aktiia.features.search.data.RemoteSearchDataSourceImpl
import com.aktiia.features.search.domain.RemoteSearchDataSource

class RemoteSearchDataSourceImplTest {

    private lateinit var api: SearchApiFake
    private lateinit var remoteDataSource: RemoteSearchDataSource

    @Before
    fun setUp() {
        api = SearchApiFake()
        remoteDataSource = RemoteSearchDataSourceImpl(api)
    }

    @Test
    fun `search places`() = runBlocking {
        val places = remoteDataSource.search("query", "1,1")

        assertTrue(places is Result.Success)
        assertTrue(places.data[0].id == "1")
        assertTrue(places.data[0].isFavorite == false)
    }
}