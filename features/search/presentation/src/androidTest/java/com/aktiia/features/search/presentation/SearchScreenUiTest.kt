package com.aktiia.features.search.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import com.aktiia.core.domain.PlaceData
import com.aktiia.core.presentation.designsystem.theme.AktiiaFoursquareTheme
import org.junit.Rule
import org.junit.Test

class SearchScreenUiTest {

    @get:Rule
    val composeRule = createComposeRule()

    private val places = listOf(
        PlaceData("1", "Place One", "100", "Address 1", false),
        PlaceData("2", "Place Two", "200", "Address 2", true)
    )

    private val places2 = listOf(
        PlaceData("4", "Place Four", "500", "Address 4", true),
        PlaceData("5", "Place Five", "600", "Address 5", false)
    )

    @Test
    fun testSearchScreenUi_init_withNoLocationPermissionAndGpsDisabled() {
        composeRule.setContent {
            AktiiaFoursquareTheme {
                SearchScreenContent(
                    state = SearchState(
                        isLoading = true
                    ),
                    searchQuery = "",
                    onQueryChange = {},
                    onPlaceClick = {},
                    onFavoriteClick = {},
                    onAction = {},
                )
            }
        }

        composeRule.onNodeWithText("Hello").assertDoesNotExist()
        composeRule.onNodeWithTag("Warning Banner").assertExists()
        composeRule.onNodeWithTag("Circular Progress").assertExists()
        composeRule.onNodeWithText("Please allow Location Permission").assertExists()
        composeRule.onNodeWithText("Search").assertExists()
        composeRule.onNodeWithTag("Favorites Icon").assertExists()
    }

    @Test
    fun testSearchScreenUi_init_withGpsDisabled() {
        composeRule.setContent {
            AktiiaFoursquareTheme {
                SearchScreenContent(
                    state = SearchState(
                        isLoading = true,
                        isLocationPermissionGranted = true
                    ),
                    searchQuery = "",
                    onQueryChange = {},
                    onPlaceClick = {},
                    onFavoriteClick = {},
                    onAction = {},
                )
            }
        }

        composeRule.onNodeWithTag("Warning Banner").assertExists()
        composeRule.onNodeWithTag("Circular Progress").assertExists()
        composeRule.onNodeWithText("Search").assertExists()
        composeRule.onNodeWithText("Enable GPS Location option in settings").assertExists()
        composeRule.onNodeWithText("Hello").assertDoesNotExist()
        composeRule.onNodeWithTag("Favorites Icon").assertExists()
    }

    @Test
    fun testSearchScreenUi_init_bothLocationAndGpsGranted() {
        composeRule.setContent {
            AktiiaFoursquareTheme {
                SearchScreenContent(
                    state = SearchState(
                        isLoading = true,
                        isLocationPermissionGranted = true,
                        isLocationEnabled = true
                    ),
                    searchQuery = "",
                    onQueryChange = {},
                    onPlaceClick = {},
                    onFavoriteClick = {},
                    onAction = {},
                )
            }
        }

        composeRule.onNodeWithTag("Warning Banner").assertDoesNotExist()
        composeRule.onNodeWithTag("Circular Progress").assertExists()
        composeRule.onNodeWithText("Search").assertExists()
        composeRule.onNodeWithText("Hello").assertDoesNotExist()
        composeRule.onNodeWithTag("Favorites Icon").assertExists()
    }

    @Test
    fun testSearchScreenUi_bothCacheAndResultAreEmpty() {
        composeRule.setContent {
            AktiiaFoursquareTheme {
                SearchScreenContent(
                    state = SearchState(),
                    searchQuery = "",
                    onQueryChange = {},
                    onPlaceClick = {},
                    onFavoriteClick = {},
                    onAction = {},
                )
            }
        }

        composeRule.onNodeWithTag("Circular Progress").assertDoesNotExist()
        composeRule.onNodeWithTag("All Is Empty Icon").assertExists()
        composeRule.onNodeWithText("Search").assertExists()
        composeRule.onNodeWithText("Hello").assertExists()
        composeRule.onNodeWithText("Start with using search at the top of the screen")
            .assertExists()
    }

    @Test
    fun testSearchScreenUi_EmptyResult() {
        composeRule.setContent {
            AktiiaFoursquareTheme {
                SearchScreenContent(
                    state = SearchState(
                        isEmptyResult = true,
                        allCachedPlaces = places
                    ),
                    searchQuery = "",
                    onQueryChange = {},
                    onPlaceClick = {},
                    onFavoriteClick = {},
                    onAction = {},
                )
            }
        }

        composeRule.onNodeWithTag("Circular Progress").assertDoesNotExist()
        composeRule.onNodeWithText("Search").assertExists()
        composeRule.onNodeWithTag("Empty Result Icon").assertExists()
        composeRule.onNodeWithText("No result").assertExists()
        composeRule.onNodeWithText("Try with different search").assertExists()
    }

    @Test
    fun testSearchScreenUi_ErrorResult() {
        composeRule.setContent {
            AktiiaFoursquareTheme {
                SearchScreenContent(
                    state = SearchState(
                        isErrorResult = true,
                        allCachedPlaces = places
                    ),
                    searchQuery = "",
                    onQueryChange = {},
                    onPlaceClick = {},
                    onFavoriteClick = {},
                    onAction = {},
                )
            }
        }

        composeRule.onNodeWithTag("Circular Progress").assertDoesNotExist()
        composeRule.onNodeWithText("Search").assertExists()
        composeRule.onNodeWithTag("Error Result Icon").assertExists()
        composeRule.onNodeWithText("Error").assertExists()
        composeRule.onNodeWithText("Error occurred, please check internet connection and try again")
            .assertExists()
    }

    @Test
    fun testSearchScreenUi_showCachedResult() {
        composeRule.setContent {
            AktiiaFoursquareTheme {
                Column {
                    PlacesList(
                        state = SearchState(
                            allCachedPlaces = places,
                            searchPlaces = places2
                        ),
                        onPlaceClick = {},
                        onAction = {},
                    )
                }
            }
        }

        composeRule.onNodeWithTag("Circular Progress").assertDoesNotExist()
        composeRule.onNodeWithText("Place One").assertExists()
        composeRule.onNodeWithText("All cached places").assertExists()
        composeRule.onNodeWithText("Place Four").assertDoesNotExist()
    }

    @Test
    fun testSearchScreenUi_showCSearchResult() {
        composeRule.setContent {
            AktiiaFoursquareTheme {
                Column {
                    PlacesList(
                        state = SearchState(
                            allCachedPlaces = places,
                            searchPlaces = places2,
                            showCachedPlaces = false
                        ),
                        onPlaceClick = {},
                        onAction = {},
                    )
                }
            }
        }

        composeRule.onNodeWithTag("Circular Progress").assertDoesNotExist()
        composeRule.onNodeWithText("Place One").assertDoesNotExist()
        composeRule.onNodeWithText("Search result").assertExists()
        composeRule.onNodeWithText("Place Four").assertExists()
    }


}