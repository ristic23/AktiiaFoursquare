@file:OptIn(ExperimentalCoroutinesApi::class)

package com.aktiia.features.search.data.remote

import app.cash.turbine.test
import com.aktiia.core.domain.PlaceData
import com.aktiia.core.domain.favorites.LocaleFavoritesDataSource
import com.aktiia.core.domain.util.DataError
import com.aktiia.core.domain.util.Result
import com.aktiia.core.domain.util.asEmptyDataResult
import com.aktiia.features.search.data.SearchRepositoryImpl
import com.aktiia.features.search.domain.LocaleSearchDataSource
import com.aktiia.features.search.domain.RemoteSearchDataSource
import com.aktiia.features.search.domain.SearchRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import java.io.IOException
import kotlin.collections.filter
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.text.contains

class SearchRepositoryImplTest {

    private val remote: RemoteSearchDataSource = mockk(relaxed = true)
    private val localSearch: LocaleSearchDataSource = mockk(relaxed = true)
    private val localFavorites: LocaleFavoritesDataSource = mockk(relaxed = true)

    private lateinit var repository: SearchRepository

    private val testDispatcher = StandardTestDispatcher()

    private fun place(id: Int, name: String, isFavorite: Boolean = false) = PlaceData(
        id = id.toString(),
        name = name,
        distance = "11",
        address = "Ulica",
        isFavorite = isFavorite
    )

    @BeforeEach
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = SearchRepositoryImpl(remote, localSearch, localFavorites)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `search emits remote success data`() = runTest {
        val data = listOf(place(id = 1, name = "Cafe"))
        coEvery { remote.search(any(), any()) } returns Result.Success(data)
        coEvery { localSearch.upsertPlaces(data) } returns Result.Success(data.map { it.id })

        repository.search("cafe", "44.0,20.0").test {
            val emission1 = awaitItem()
            assertEquals(emission1, Result.Success(data))
            cancelAndIgnoreRemainingEvents()
        }

        coVerify(exactly = 1) { localSearch.upsertPlaces(data) }
    }

    @Test
    fun `search emits error when remote fails and cache is empty`() = runTest {
        coEvery { remote.search(any(), any()) } returns Result.Error(DataError.Network.NO_INTERNET)
        coEvery { localSearch.getPlaces() } returns flowOf(emptyList())

        repository.search("cafe", "44.0,20.0").test {
            val emission1 = awaitItem()
            assertEquals(emission1, Result.Error(DataError.Network.NO_INTERNET))
            cancelAndIgnoreRemainingEvents()
        }
        coVerify(exactly = 0) { localSearch.upsertPlaces(any()) }
        coVerify(exactly = 1) { localSearch.getPlaces() }
    }

    @Test
    fun `search emits data when remote fails but cache exists`() = runTest {
        val cached = listOf(place(id = 1, name = "Cafe"), place(id = 2, name = "Bar"))
        coEvery { remote.search(any(), any()) } returns Result.Error(DataError.Network.REQUEST_TIMEOUT)
        coEvery { localSearch.getPlaces() } returns flowOf(cached)

        repository.search("cafe", "44.0,20.0").test {
            val expected = Result.Success(cached.filter { it.name.contains("cafe", ignoreCase = true) })
            val emission1 = awaitItem()
            assertEquals(emission1, expected)
            cancelAndIgnoreRemainingEvents()
        }
        coVerify(exactly = 0) { localSearch.upsertPlaces(any()) }
        coVerify(exactly = 1) { localSearch.getPlaces() }
    }

    @Test
    fun `updateFavoriteStatus returns true on add`() = runTest {
        coEvery { localFavorites.addFavoriteStatus("1") } returns Result.Success(true).asEmptyDataResult()

        val result = repository.updateFavoriteStatus("1", true)
        assertEquals(Result.Success(true), result)
    }

    @Test
    fun `updateFavoriteStatus returns false on remove`() = runTest {
        coEvery { localFavorites.removeFavoriteStatus("1") } returns Result.Success(false).asEmptyDataResult()

        val result = repository.updateFavoriteStatus("1", false)
        assertEquals(Result.Success(false), result)
    }

    @Test
    fun `updateFavoriteStatus returns error on exception`() = runTest {
        coEvery { localFavorites.removeFavoriteStatus("1") } throws IOException("disk full")

        val result = repository.updateFavoriteStatus("1", false)
        assertEquals(Result.Error(DataError.Local.DISK_FULL), result)
    }
}
