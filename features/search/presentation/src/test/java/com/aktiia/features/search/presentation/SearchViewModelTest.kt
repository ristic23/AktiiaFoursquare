@file:OptIn(ExperimentalCoroutinesApi::class)

package com.aktiia.features.search.presentation
import com.aktiia.core.domain.PlaceData
import com.aktiia.core.domain.location.LocationProvider
import com.aktiia.core.domain.util.DataError
import com.aktiia.core.domain.util.Result
import com.aktiia.features.search.domain.SearchRepository
import com.aktiia.features.search.presentation.SearchAction.DismissRationaleDialog
import com.aktiia.features.search.presentation.SearchAction.OnFavoriteClick
import com.aktiia.features.search.presentation.SearchAction.OnSearchClear
import com.aktiia.features.search.presentation.SearchAction.SubmitLocationPermissionInfo
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

class SearchViewModelTest {

    private val searchRepository: SearchRepository = mockk(relaxed = true)
    private val locationProvider: LocationProvider = mockk(relaxed = true)
    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    private lateinit var viewModel: SearchViewModel

    @BeforeTest
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun place(id: String, name: String, isFavorite: Boolean = false) = PlaceData(
        id = id,
        name = name,
        distance = "11",
        address = "Ulica",
        isFavorite = isFavorite
    )
    private val resultPlaces = listOf(
        place(id = "1", name = "Park", isFavorite = false),
        place(id = "2", name = "Bar", isFavorite = true)
    )

    @Test
    fun `on init loads cached places and sets loading to false`() = testScope.runTest {
        every { searchRepository.getAllCached() } returns flowOf(resultPlaces)

        viewModel = SearchViewModel(searchRepository, locationProvider)

        advanceUntilIdle()

        assertEquals(false, viewModel.state.isLoading)
        assertEquals(resultPlaces, viewModel.state.allCachedPlaces)
    }

    @Test
    fun `on search cache empty api result success`() = testScope.runTest {
        val cached = listOf<PlaceData>()
        every { searchRepository.getAllCached() } returns flowOf(cached)
        coEvery { locationProvider.getLocation() } returns "12.34,56.78"
        coEvery {
            searchRepository.search(
                any(),
                any()
            )
        } returns flowOf(Result.Success(resultPlaces))

        viewModel = SearchViewModel(searchRepository, locationProvider)

        advanceUntilIdle()

        viewModel.onQueryChange("ar")
        advanceTimeBy(400L)

        assertEquals(false, viewModel.state.isLoadingSearch)
        assertEquals(false, viewModel.state.isErrorResult)
        assertEquals(false, viewModel.state.isEmptyResult)
        assertEquals(resultPlaces, viewModel.state.searchPlaces)
        coVerify(exactly = 1) { searchRepository.search(any(), any()) }
        coVerify(exactly = 1) { locationProvider.getLocation() }
    }

    @Test
    fun `on search cache success api result error`() = testScope.runTest {
        every { searchRepository.getAllCached() } returns flowOf(resultPlaces)
        coEvery { locationProvider.getLocation() } returns "12.34,56.78"
        coEvery {
            searchRepository.search(
                any(),
                any()
            )
        } returns flowOf(Result.Success(resultPlaces))

        viewModel = SearchViewModel(searchRepository, locationProvider)

        advanceUntilIdle()

        viewModel.onQueryChange("ar")
        advanceTimeBy(400L)

        assertEquals(false, viewModel.state.isLoadingSearch)
        assertEquals(false, viewModel.state.isErrorResult)
        assertEquals(false, viewModel.state.isEmptyResult)
        assertEquals(resultPlaces, viewModel.state.searchPlaces)
        coVerify(exactly = 1) { searchRepository.search(any(), any()) }
        coVerify(exactly = 1) { locationProvider.getLocation() }
    }

     @Test
    fun `on search result error`() = testScope.runTest {
        coEvery { locationProvider.getLocation() } returns "12.34,56.78"
        coEvery {
            searchRepository.search(
                any(),
                any()
            )
        } returns flowOf(Result.Error(DataError.Network.NO_INTERNET))

        viewModel = SearchViewModel(searchRepository, locationProvider)

        advanceUntilIdle()

        viewModel.onQueryChange("ar")
        advanceTimeBy(400L)

        assertEquals(true, viewModel.state.isErrorResult)
        coVerify(exactly = 1) { searchRepository.search(any(), any()) }
        coVerify(exactly = 1) { locationProvider.getLocation() }
    }

    @Test
    fun `on action OnFavoriteClick`() = testScope.runTest {
        val favoriteAction = OnFavoriteClick(id = "1", true)
        coEvery { searchRepository.updateFavoriteStatus(any(), any()) } returns Result.Success(true)
        val cached = listOf<PlaceData>()
        val resultPlaces = listOf(
            place(id = "1", name = "Park", isFavorite = false),
            place(id = "2", name = "Bar", isFavorite = true)
        )

        every { searchRepository.getAllCached() } returns flowOf(cached)
        coEvery { locationProvider.getLocation() } returns "12.34,56.78"
        coEvery {
            searchRepository.search(
                any(),
                any()
            )
        } returns flowOf(Result.Success(resultPlaces))

        viewModel = SearchViewModel(searchRepository, locationProvider)
        viewModel.onQueryChange("ar")
        advanceTimeBy(400L)
        assertEquals(false, viewModel.state.showCachedPlaces)

        viewModel.onAction(favoriteAction)
        advanceUntilIdle()
        assertEquals(2, viewModel.state.searchPlaces.size)
        assertEquals(true, viewModel.state.searchPlaces[0].isFavorite)
    }

    @Test
    fun `on action OnSearchClear`() = testScope.runTest {
        coEvery {
            searchRepository.search(
                any(),
                any()
            )
        } returns flowOf(Result.Success(resultPlaces))

        viewModel = SearchViewModel(searchRepository, locationProvider)
        viewModel.onQueryChange("ar")
        assertEquals(true, viewModel.state.showCachedPlaces)
        advanceTimeBy(400L)
        assertEquals(false, viewModel.state.showCachedPlaces)

        viewModel.onAction(OnSearchClear)
        advanceUntilIdle()
        assertEquals(true, viewModel.state.showCachedPlaces)
    }

    @Test
    fun `on action SubmitLocationPermissionInfo then DismissRationaleDialog`() = testScope.runTest {

        viewModel = SearchViewModel(searchRepository, locationProvider)

        viewModel.onAction(SubmitLocationPermissionInfo(showLocationRationale = true))
        advanceUntilIdle()
        assertEquals(true, viewModel.state.showLocationRationale)

        viewModel.onAction(DismissRationaleDialog)
        advanceUntilIdle()
        assertEquals(true, viewModel.state.showCachedPlaces)
    }
}